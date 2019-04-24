package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.winshare.edu.modules.system.entity.OrgInfo;
import com.winshare.edu.modules.system.entity.TreeInfo;

import tk.mybatis.mapper.common.Marker;

public interface OrgInfoMapper extends Marker{
	
	OrgInfo getById(Long id);
	
	OrgInfo getByExample(OrgInfo orgInfo);

	List<OrgInfo> findList(OrgInfo orgInfo);
	
	List<TreeInfo> findTreeInfo(TreeInfo treeInfo);
	
	int save(OrgInfo orgInfo);
	
	int update(OrgInfo orgInfo);
	
	int delete(Long id);
	
	int updateStatus(Map<String,Object> params);
	
	List<OrgInfo> findListChildren(OrgInfo orgInfo);
}
