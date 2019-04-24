package com.winshare.edu.modules.classes.service;

import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.modules.classes.entity.ClassGroup;
import com.winshare.edu.modules.classes.entity.H10007_V1_0_GroupList;
import com.winshare.edu.modules.classes.entity.RGroupStudent;
import com.winshare.edu.modules.classes.mapper.ClassGroupMapper;
import com.winshare.edu.modules.classes.mapper.RClassGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class ClassGroupService {

    @Autowired
    private ClassGroupMapper classGroupMapper;
    @Autowired
    private RClassGroupMapper rClassGroupMapper;

    public List<ClassGroup> getClassGroup(Long classId, Long teacherId, String grade, String subject) {
        ClassGroup classGroup = new ClassGroup();
        classGroup.setClassId(Integer.parseInt(classId.toString()));
        classGroup.setTeacherId(Integer.parseInt(teacherId.toString()));
        classGroup.setGrade(grade);
        classGroup.setSubject(subject);

        return classGroupMapper.getClassGroup(classGroup);
    }

    @Transactional
    public void getClassGroupDeal(Long classId, Long teacherId, String subject,String grade, List<H10007_V1_0_GroupList> students) throws Exception {
        List<String> stuList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudents().length > 0) {
                for (int j = 0; j < students.get(i).getStudents().length; j++) {
                    stuList.add(String.valueOf(students.get(i).getStudents()[j]));
                }
            }
        }
        boolean isRepeat = stuList.size() != new HashSet<String>(stuList).size();
        if(isRepeat){
            throw new BusinessException("0","存在重复学生!");
        }

        ClassGroup classGroup = new ClassGroup();
        classGroup.setClassId(Integer.parseInt(classId.toString()));
        classGroup.setTeacherId(Integer.parseInt(teacherId.toString()));
        classGroup.setGrade(grade);
        classGroup.setSubject(subject);

        for (int i = 0; i < students.size(); i++) {
            Long groupId = students.get(i).getGroupId();
            if (null != groupId && !"".equals(String.valueOf(groupId))) {
                //删除关系表数据重新插入学生分组关系
                rClassGroupMapper.delete(groupId);
                if(students.get(i).getStudents().length == 0){
                    //改组被删除则删除原有分组
                    classGroupMapper.delete(groupId);
                }else{
                    //修改原有分组
                    classGroup.setLeaderId(Integer.parseInt(String.valueOf(students.get(i).getLeaderId())));
                    classGroup.setGroupName(students.get(i).getGroupName());
                    classGroup.setId(groupId);
                    //修改分组
                    classGroupMapper.update(classGroup);
                    //更新学生分组关系表
                    for (int j = 0; j < students.get(i).getStudents().length; j++) {
                        RGroupStudent rGroupStudent = new RGroupStudent();
                        rGroupStudent.setGroupId(Integer.parseInt(String.valueOf(groupId)));
                        rGroupStudent.setStudentId(Integer.parseInt(String.valueOf(students.get(i).getStudents()[j])));
                        rClassGroupMapper.insert(rGroupStudent);
                    }
                }
            } else {
                //新增分组
                classGroup.setLeaderId(Integer.parseInt(String.valueOf(students.get(i).getLeaderId())));
                classGroup.setGroupName(students.get(i).getGroupName());
                classGroup.setGroupName(students.get(i).getGroupName());
                classGroup.setIsStatus("0");
                classGroup.setGroupScore(0);
                classGroup.setCreateTime(new Date());
                classGroup.setModifyTime(new Date());
                classGroupMapper.insert(classGroup);
                //插入学生分组关系表
                for (int j = 0; j < students.get(i).getStudents().length; j++) {
                    RGroupStudent rGroupStudent = new RGroupStudent();
                    rGroupStudent.setGroupId(Integer.parseInt(String.valueOf(classGroup.getId())));
                    rGroupStudent.setStudentId(Integer.parseInt(String.valueOf(students.get(i).getStudents()[j])));
                    rClassGroupMapper.insert(rGroupStudent);
                }
            }
        }

    }

}
