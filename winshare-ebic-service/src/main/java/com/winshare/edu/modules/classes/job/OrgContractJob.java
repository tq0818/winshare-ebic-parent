package com.winshare.edu.modules.classes.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.modules.system.entity.OrgPower;
import com.winshare.edu.modules.system.service.OrgInfoService;
import com.winshare.edu.modules.system.service.OrgPowerService;

public class OrgContractJob {

	private OrgPowerService orgPowerService;
	
	private OrgInfoService orgInfoService;
	
	private static Logger log = Logger.getLogger(OrgContractJob.class);
	
	private void process() throws InterruptedException{
		OrgPower orgPower = new OrgPower();
		orgPower.setEndTime(new Date());
		int pageNum = 1;
		int pageSize = 100;
		List<Long> orgIdList = new ArrayList<Long>();
		List<Long> orgPowerIdList = new ArrayList<Long>();
		List<Map<String,Integer>> mapList = orgPowerService.findOverdueList(pageNum, pageSize, orgPower);
		while(mapList.size() > 0){
			for(Map<String,Integer> map : mapList){
				orgIdList.add(Long.valueOf(map.get("orgId").toString()));
				orgPowerIdList.add(Long.valueOf(map.get("orgPowerId").toString()));
			}
			if(orgIdList.size() > 0){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("idList", orgIdList);
				params.put("status",0);
				orgInfoService.updateStatus(params);
				orgIdList = new ArrayList<Long>();
			}
			if(orgPowerIdList.size() > 0){
				Map<String,Object> params2 = new HashMap<String,Object>();
				params2.put("idList", orgPowerIdList);
				params2.put("status",0);
				orgPowerService.updateStatus(params2);
				orgPowerIdList = new ArrayList<Long>();
				
			}
			pageNum++;
			mapList = orgPowerService.findOverdueList(pageNum, pageSize, orgPower);	
						
		}
	
	}
	
	
	public void work(){
		try{
			process();
		}catch(Exception e){
			log.error("OrgContractJob is error ", e);
		}
	}


	public OrgPowerService getOrgPowerService() {
		return orgPowerService;
	}


	public void setOrgPowerService(OrgPowerService orgPowerService) {
		this.orgPowerService = orgPowerService;
	}


	public OrgInfoService getOrgInfoService() {
		return orgInfoService;
	}


	public void setOrgInfoService(OrgInfoService orgInfoService) {
		this.orgInfoService = orgInfoService;
	}
	
	
}
