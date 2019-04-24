package com.winshare.edu.modules.system.mapper;

import java.util.List;

import com.winshare.edu.modules.system.entity.SysLog;
import com.winshare.edu.modules.system.entity.SysRole;

import tk.mybatis.mapper.common.Marker;

public interface SysLogMapper extends Marker{

	List<SysLog> findList(SysLog sysLog);
	
	int save(SysLog sysLog);
}
