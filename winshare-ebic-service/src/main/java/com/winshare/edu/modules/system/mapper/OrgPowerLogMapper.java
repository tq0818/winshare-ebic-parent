package com.winshare.edu.modules.system.mapper;

import java.util.List;

import com.winshare.edu.modules.system.entity.OrgPowerLog;

public interface OrgPowerLogMapper {

	public int save(OrgPowerLog orgPowerLog);
	
	public List<OrgPowerLog> findList(OrgPowerLog orgPowerLog);
	
}
