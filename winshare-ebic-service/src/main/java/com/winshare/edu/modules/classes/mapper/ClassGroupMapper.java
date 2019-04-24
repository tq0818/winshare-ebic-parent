package com.winshare.edu.modules.classes.mapper;

import com.winshare.edu.modules.classes.entity.ClassGroup;
import com.winshare.edu.modules.classes.entity.ClassInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface ClassGroupMapper extends Marker{

    void insert(ClassGroup classGroup);

    void delete(Long id);

    void update(ClassGroup classGroup);

    String findScoreCount(Long id);

    List<ClassGroup> getClassGroup(ClassGroup classGroup);


}
