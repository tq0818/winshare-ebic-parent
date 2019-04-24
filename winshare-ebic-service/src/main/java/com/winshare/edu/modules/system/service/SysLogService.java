package com.winshare.edu.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.SysLog;
import com.winshare.edu.modules.system.entity.SysRole;
import com.winshare.edu.modules.system.mapper.SysLogMapper;

@Service("sysLogService")
public class SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	public List<SysLog> find(PageRequest pageRequest,SysLog sysLog){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		return sysLogMapper.findList(sysLog);
	}
	
	public int save(SysLog sysLog){
		int row = 0;
		row = sysLogMapper.save(sysLog);
		return row;
	}
}
