package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.ClassGroup;
import com.winshare.edu.modules.classes.entity.RGroupStudent;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface RClassGroupMapper extends Marker{

    void insert(RGroupStudent rGroupStudent);

    void delete(Long id);

    List<RGroupStudent> findStuByGroupId(Long id);


}
