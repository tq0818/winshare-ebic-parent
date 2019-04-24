package com.winshare.edu.modules.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.modules.system.entity.OrgPower;
import com.winshare.edu.modules.system.mapper.OrgPowerMapper;

@Service("orgPowerService")
public class OrgPowerService {

   @Autowired
   private OrgPowerMapper orgPowerMapper;	
	
   public int save(OrgPower orgPower){
	   return orgPowerMapper.save(orgPower);
   }
	
   public int update(OrgPower orgPower){
		return orgPowerMapper.update(orgPower);
   }
	
  public List<OrgPower> findList(PageRequest pageRequest,OrgPower orgPower){
		PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getOrderBy());
	    return orgPowerMapper.findList(orgPower);
  }
  
  public OrgPower getById(Long id){
	  return orgPowerMapper.getById(id);
  }
  
  public int countContractNumber(OrgPower orgPower){
	  int row = orgPowerMapper.countContractNumber(orgPower);
	  return row;
  }
  
  public List<Map<String,Integer>> findOverdueList(int pageNum,int pageSize,OrgPower orgPower){
	    PageHelper.startPage(pageNum, pageSize, "");
	    return orgPowerMapper.findOverdueList(orgPower);
  }
  
  public int updateStatus(Map<String,Object> params){
	     return orgPowerMapper.updateStatus(params);
  }
  
}
