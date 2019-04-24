package com.winshare.edu.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winshare.edu.modules.system.entity.SysOperation;
import com.winshare.edu.modules.system.mapper.SysOperationMapper;

@Service("sysOperationService")
public class SysOperationService {

	@Autowired
	private SysOperationMapper sysOperationMapper;
	
	public List<SysOperation> findOperationByMenuId(Long menuId){
		return sysOperationMapper.findOperationByMenuId(menuId);
	}
	
	public List<SysOperation> findAll(Long roleId){
		return sysOperationMapper.findAll(roleId);
	}
	
  	
	
	public List<SysOperation> findOperationByAccountId(Long accountId){
		return sysOperationMapper.findOperationByAccountId(accountId);
	}
	
	
}
