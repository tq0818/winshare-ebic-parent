package com.winshare.edu.modules.system.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;

public class SysLog extends BaseEntity<SysLog>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8824638823934041872L;

	private String account;
	
	private String userName;
	
	private String orgName;
	
	private String startTime;
	
	private String endTime;

	private Long orgId;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	
}
