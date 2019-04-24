package com.winshare.edu.modules.classes.service;

import com.winshare.edu.modules.classes.entity.TeacherCourse;
import com.winshare.edu.modules.classes.entity.TeacherScoreItem;
import com.winshare.edu.modules.classes.mapper.TeacherCourseMapper;
import com.winshare.edu.modules.classes.mapper.TeacherScoreItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherScoreItemService {
    @Autowired
    private TeacherScoreItemMapper teacherScoreItemMapper;

    public List<TeacherScoreItem> getTeacherScoreItem(Long teacherId, Long classId,String subject){
        TeacherScoreItem teacherScoreItem = new TeacherScoreItem();
        teacherScoreItem.setClassId(Integer.parseInt(String.valueOf(classId)));
        teacherScoreItem.setTeacherId(Integer.parseInt(String.valueOf(teacherId)));
        teacherScoreItem.setSubjcet(subject);
        return teacherScoreItemMapper.getTeacherScoreItem(teacherScoreItem);
    }

    public void saveTeacherScoreItem(Long teacherId, Long classId,String subject,String itemName,Integer score){
        TeacherScoreItem teacherScoreItem = new TeacherScoreItem();
        teacherScoreItem.setClassId(Integer.parseInt(String.valueOf(classId)));
        teacherScoreItem.setTeacherId(Integer.parseInt(String.valueOf(teacherId)));
        teacherScoreItem.setSubjcet(subject);
        teacherScoreItem.setItemName(itemName);
        teacherScoreItem.setScore(score);
        teacherScoreItemMapper.insert(teacherScoreItem);
    }

    public void delete(Long itemId){
        teacherScoreItemMapper.delete(itemId);
    }

}
