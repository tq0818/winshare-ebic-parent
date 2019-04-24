package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.TeacherCourse;
import tk.mybatis.mapper.common.Marker;

import java.util.List;
import java.util.Map;

public interface TeacherCourseMapper extends Marker{

    void insert(TeacherCourse teacherCourset);

    void update(TeacherCourse teacherCourset);

    void updateTeacherCourse(TeacherCourse teacherCourset);

    TeacherCourse findTeacherInfo(TeacherCourse teacherCourset);

    TeacherCourse findTeacherInfoByStatus(TeacherCourse teacherCourset);

    List<TeacherCourse> stuFindTeacherInfoByStu(Map<String,Object> map);

}
