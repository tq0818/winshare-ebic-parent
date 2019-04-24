package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.TeacherScoreItem;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface TeacherScoreItemMapper extends Marker{

    List<TeacherScoreItem> getTeacherScoreItem(TeacherScoreItem teacherScoreItem);

    void insert(TeacherScoreItem teacherScoreItem);

    void delete(Long itemId);

}
