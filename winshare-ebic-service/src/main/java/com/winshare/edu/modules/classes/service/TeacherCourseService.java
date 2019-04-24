package com.winshare.edu.modules.classes.service;

import com.alibaba.fastjson.JSONObject;
import com.winshare.edu.common.exceptions.BusinessException;
import com.winshare.edu.modules.classes.entity.TeacherCourse;
import com.winshare.edu.modules.classes.mapper.TeacherCourseMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TeacherCourseService {
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    public Long saveTeacherInfo(Long classId,Long teacherId,String subject,String teacherName,
                                         String  teacherProtocol,String  teacherIp,String teacherPort,
                                         String gradeName, String subjectName,String bookName,
                                         String chapterName ){

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setClassId(Integer.parseInt(String.valueOf(classId)));
        teacherCourse.setTeacherId(Integer.parseInt(String.valueOf(teacherId)));
        teacherCourse.setSubjcet(subject);
        teacherCourse.setTeacherName(teacherName);
        teacherCourse.setTeacherProtocol(teacherProtocol);
        teacherCourse.setTeacherIp(teacherIp);
        teacherCourse.setTeacherPort(teacherPort);
        teacherCourse.setGradeName(gradeName);
        teacherCourse.setSubjcetName(subjectName);
        teacherCourse.setBookName(bookName);
        teacherCourse.setChapterName(chapterName);
        teacherCourse.setCourseStatus("1");
        TeacherCourse tea = teacherCourseMapper.findTeacherInfo(teacherCourse);

        if(null != tea){
            teacherCourse.setId(tea.getCourseId());
            teacherCourseMapper.updateTeacherCourse(teacherCourse);
        }else{
            teacherCourseMapper.insert(teacherCourse);
        }

        return teacherCourse.getId();
    }

    public void updateTeacherInfo(Long courseId){
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setId(courseId);
        teacherCourse.setCourseStatus("0");
        teacherCourseMapper.update(teacherCourse);

    }

    public TeacherCourse findTeacherInfo(Long classId,Long teacherId,String subject){
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setClassId(Integer.parseInt(String.valueOf(classId)));
        teacherCourse.setTeacherId(Integer.parseInt(String.valueOf(teacherId)));
        teacherCourse.setSubjcet(subject);

        TeacherCourse teacherCourse1 = teacherCourseMapper.findTeacherInfoByStatus(teacherCourse);
        
        if(teacherCourse1 == null){
        	return null;
        }else{
            JSONObject json = new JSONObject();
            BusinessException businessException = new BusinessException();
            businessException.setCode("0");
            businessException.setMsg("检测到上节课是异常退出，是否继续上课");
            json.put("courseId", teacherCourse1.getCourseId());
            businessException.setParam(json);
            throw businessException;
        }
    }

    public List<TeacherCourse> stuFindTeacherInfoByStu(String account){
        Map<String,Object> map = new HashMap<>();
        map.put("account",account);

        return teacherCourseMapper.stuFindTeacherInfoByStu(map);
    }
}
