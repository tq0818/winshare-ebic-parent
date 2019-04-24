package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.StudentScoreItem;
import tk.mybatis.mapper.common.Marker;

public interface StudentScoreItemMapper extends Marker{

    void insert(StudentScoreItem studentScoreItem);

}
