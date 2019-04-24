package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import tk.mybatis.mapper.common.Marker;

import com.winshare.edu.modules.system.entity.SysMenu;
import com.winshare.edu.modules.system.entity.SysOperation;
import com.winshare.edu.modules.system.entity.TreeInfo;

public interface SysOperationMapper extends Marker{

	List<SysOperation> findOperationByMenuId(Long menuId);
	
	List<SysOperation> findAll(Long roleId);
	
	int deleteOperRelaByRoleId(Long roleId);
	
	int saveOperRela(Map<String,Object> map);
	
	List<SysOperation> findOperationByAccountId(Long accountId);
}
