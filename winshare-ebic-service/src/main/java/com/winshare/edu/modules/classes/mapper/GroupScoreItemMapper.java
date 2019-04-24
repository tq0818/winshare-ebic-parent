package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.GroupScoreItem;
import tk.mybatis.mapper.common.Marker;

public interface GroupScoreItemMapper extends Marker{

    void insert(GroupScoreItem groupScoreItem);

}
