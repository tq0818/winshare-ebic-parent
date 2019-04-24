package com.winshare.edu.modules.system.mapper;

import java.util.List;

import tk.mybatis.mapper.common.Marker;
import com.winshare.edu.modules.system.entity.AreaInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;

public interface AreaInfoMapper extends Marker{

	AreaInfo getById(Long id);
	
	List<AreaInfo> findList(AreaInfo areaInfo);
	
	List<TreeInfo> findTreeInfo(TreeInfo treeInfo);
	
	int save(AreaInfo areaInfo);
	
	int update(AreaInfo areaInfo);
	
	int delete(Long id);
	
	List<AreaInfo> findListByParentId(AreaInfo areaInfo);
	
	int countByCode(AreaInfo areaInfo);
	
	List<AreaInfo> findListByChildrenName(AreaInfo areaInfo);
	
}
