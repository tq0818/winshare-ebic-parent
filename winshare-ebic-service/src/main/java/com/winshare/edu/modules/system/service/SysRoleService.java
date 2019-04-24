package com.winshare.edu.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.utils.SpringContextHolder;
import com.winshare.edu.modules.system.entity.SysRole;
import com.winshare.edu.modules.system.entity.TreeInfo;
import com.winshare.edu.modules.system.mapper.SysOperationMapper;
import com.winshare.edu.modules.system.mapper.SysRoleMapper;

@Service("sysRoleService")
public class SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysOperationMapper sysOperationMapper;
	
	public SysRole get(Long id){
		return sysRoleMapper.getById(id);
	}
	
	public int save(SysRole sysRole){
		int row = 0;
		if(sysRole.getId() == null){
		   row = sysRoleMapper.save(sysRole);			
		}else{
		   row = sysRoleMapper.update(sysRole);
		}
		return row;
	}
	
	
	public int delete(Long id){
		return sysRoleMapper.delete(id);
	}
	
	
	
	public List<SysRole> find(PageRequest pageRequest, SysRole sysRole){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		try{
			return sysRoleMapper.findList(sysRole);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SysRole> find(SysRole sysRole){
		return sysRoleMapper.findList(sysRole);
	}
	
	public List<SysRole> findListByParentId(SysRole sysRole){
		return sysRoleMapper.findListByParentId(sysRole);
	}
	
	public void saveAuthorizeRole(Long roleId,List<Long> menuIdList,List<Long> operList){
		sysRoleMapper.deleteRoleAuthorize(roleId);
		int delRow = sysOperationMapper.deleteOperRelaByRoleId(roleId);
		for(Long menuId : menuIdList){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", roleId);
			params.put("menuId", menuId);
			sysRoleMapper.saveAuthorizeRole(params);
		}
		for(Long operId: operList){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("operationId", operId);
			params.put("roleId", roleId);
			int saveRow = sysOperationMapper.saveOperRela(params);
		}
    	
    	
	}
	
	public List<Long> findMenuIdListByRoleId(Long roleId){
		return sysRoleMapper.findMenuIdListByRoleId(roleId);
	}
	
	public int batchUpdateStatus(List<Long> roleIdList,Integer status){
		Map<String,Object> params = new HashMap<String,Object>();
	    params.put("status", status);
		params.put("roleIdList", roleIdList);
		int row = sysRoleMapper.batchUpdateStatus(params);
		return row;
	}
	
	public List<Long>  findRoleIdListByAccountId(long accountId){
		return sysRoleMapper.findRoleIdListByAccountId(accountId);
	}
	
	public int saveUserRoleRelation(Map<String,Object> params){
		int saveRow = 0;
		sysRoleMapper.delRelationByAccountId((Long)params.get("accountId"));
		saveRow = sysRoleMapper.saveUserRoleRelation(params);
		return saveRow;
	}
	
	
	public int countByNameIden(SysRole sysRole){
		int count = 0;
		count = sysRoleMapper.countByNameIden(sysRole);
		return count;
	}
}
