package com.winshare.edu.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.winshare.edu.modules.system.entity.OrgPower;

public interface OrgPowerMapper {

	int save(OrgPower orgPower);
	
	int update(OrgPower orgPower);
	
	List<OrgPower> findList(OrgPower orgPower);
	
	OrgPower getById(Long id);
	
    int countContractNumber(OrgPower orgPower);
    
    List<Map<String,Integer>> findOverdueList(OrgPower orgPower);
    
    int updateStatus(Map<String,Object> params);
	
}
