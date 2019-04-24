package com.winshare.edu.modules.classes.service;

import com.winshare.edu.modules.classes.entity.*;
import com.winshare.edu.modules.classes.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupScoreService {

    @Autowired
    private GroupScoreMapper groupScoreMapper;
    @Autowired
    private GroupScoreItemMapper groupScoreItemMapper;
    @Autowired
    private ClassGroupMapper classGroupMapper;
    @Autowired
    private RClassGroupMapper rClassGroupMapper;
    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private StudentScoreItemMapper studentScoreItemMapper;

    public void groupBonusPoints(Long id, String itemName, Integer score, String bookName, String chapterName) {
        //增加明细
        GroupScoreItem groupScoreItem = new GroupScoreItem();
        groupScoreItem.setBookName(bookName);
        groupScoreItem.setChapterName(chapterName);
        groupScoreItem.setGroupId(Integer.parseInt(String.valueOf(id)));
        groupScoreItem.setItemName(itemName);
        groupScoreItem.setScore(score);
        groupScoreItem.setCreateTime(new Date());
        groupScoreItem.setModifyTime(new Date());
        groupScoreItemMapper.insert(groupScoreItem);

        String groupScore="";
        //查询当前小组总分
        try {
             groupScore = classGroupMapper.findScoreCount(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        //更新小组总分
        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(id);
        if(null != groupScore){
            classGroup.setGroupScore(Integer.parseInt(groupScore) + score);
        }else{
            classGroup.setGroupScore(score);
        }
        classGroupMapper.update(classGroup);


        //給改小组所有成员加分
        List<RGroupStudent> list = rClassGroupMapper.findStuByGroupId(id);
        //获取该小组所在的班级科目信息
        ClassGroup classGroup1 = new ClassGroup();
        classGroup1.setId(id);
        List<ClassGroup> classGroupList = classGroupMapper.getClassGroup(classGroup1);
        for(RGroupStudent rs : list){
            //获取该学生当前积分
            StudentScore studentScore = new StudentScore();
            studentScore.setStudentId(Integer.parseInt(String.valueOf(rs.getStudentId())));
            studentScore.setTeacherId(Integer.parseInt(String.valueOf(classGroupList.get(0).getTeacherId())));
            studentScore.setGrade(classGroupList.get(0).getGrade());
            studentScore.setSubject(classGroupList.get(0).getSubject());
            StudentScore student = studentScoreMapper.findSutdent(studentScore);
            if(null != student){
                if(null != student.getStudentScore() && !"".equals(String.valueOf(student.getStudentScore()))){
                    studentScore.setStudentScore(score+student.getStudentScore());
                }
            }else{
                studentScore.setStudentScore(score);
            }

            if(null != student){
                studentScore.setId(student.getId());
                //修改学生积分
                studentScore.setModifyTime(new Date());
                studentScoreMapper.update(studentScore);
            }else{
                //新增积分
                studentScore.setCreateTime(new Date());
                studentScore.setModifyTime(new Date());
                studentScoreMapper.insert(studentScore);
            }
            //增加该学生积分明细
            StudentScoreItem stuScoreItem = new StudentScoreItem();
            stuScoreItem.setScoreId(Integer.parseInt(String.valueOf(studentScore.getId())));
            stuScoreItem.setItemName(itemName);
            stuScoreItem.setScore(score);
            stuScoreItem.setBookName(bookName);
            stuScoreItem.setChapterName(chapterName);
            stuScoreItem.setSource("1");
            stuScoreItem.setCreateTime(new Date());
            stuScoreItem.setModifyTime(new Date());
            studentScoreItemMapper.insert(stuScoreItem);
        }

        classGroupMapper.update(classGroup);
    }
}
