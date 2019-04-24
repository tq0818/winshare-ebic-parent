package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Marker;

import com.winshare.edu.modules.system.entity.SysRole;
import com.winshare.edu.modules.system.entity.TreeInfo;

public interface SysRoleMapper extends Marker{

	SysRole getById(Long id);
	
	List<SysRole> findList(SysRole SysRole);
	
	List<TreeInfo> findTreeInfo(TreeInfo treeInfo);
	
	int save(SysRole SysRole);
	
	int update(SysRole SysRole);
	
	int delete(Long id);
	
	List<SysRole> findListByParentId(SysRole SysRole);
	
    void saveAuthorizeRole(Map<String,Object> params);
	
	int deleteRoleAuthorize(Long roleId);
	
	List<Long> findMenuIdListByRoleId(long roleId);
	
	int batchUpdateStatus(Map<String,Object> params);
	
	List<Long> findRoleIdListByAccountId(long accountId);
	
	int saveUserRoleRelation(Map<String,Object> params);
	
	int delRelationByAccountId(Long accountId);
	
	int countByNameIden(SysRole sysRole);
	
	List<SysRole> findRoleListByAccountId(Long accountId);
    
}
