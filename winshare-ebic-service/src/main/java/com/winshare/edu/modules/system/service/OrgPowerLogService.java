package com.winshare.edu.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.OrgPowerLog;
import com.winshare.edu.modules.system.mapper.OrgPowerLogMapper;


@Service("orgPowerLogService")
public class OrgPowerLogService {

	
	   @Autowired
	   private OrgPowerLogMapper orgPowerLogMapper;	
		
	   public int save(OrgPowerLog orgPowerLog){
		   return orgPowerLogMapper.save(orgPowerLog);
	   }
	   
	   public List<OrgPowerLog> findList(PageRequest pageRequest,OrgPowerLog orgPowerLog){
			PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
		    return orgPowerLogMapper.findList(orgPowerLog);
	  }
}
