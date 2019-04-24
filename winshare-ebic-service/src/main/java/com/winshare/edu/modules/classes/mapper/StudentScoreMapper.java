package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.StudentScore;
import tk.mybatis.mapper.common.Marker;

public interface StudentScoreMapper extends Marker{

    StudentScore findSutdent(StudentScore studentScore);

    void update(StudentScore studentScore);

    void insert(StudentScore studentScore);

}
