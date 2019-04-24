package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.TreeEntity;

public class OrgInfo extends TreeEntity<OrgInfo>{
	
	private static final long serialVersionUID = -22684097382243222L;
	
	private String orgName;
	
	private String orgCode;
	
	private String orgNumber;
	
	private String orgLevel;
	
	private String orgContact;
	
	private String orgPhone;
	
	private String orgEmail;
	
	private String areaCode;
	
	private String address;
	
	private String orgStatus;
	
	private Long areaId;

	private String boundedOrgLevel;
	
	@Override
	public OrgInfo getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(OrgInfo parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgContact() {
		return orgContact;
	}

	public void setOrgContact(String orgContact) {
		this.orgContact = orgContact;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}



	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getBoundedOrgLevel() {
		return boundedOrgLevel;
	}

	public void setBoundedOrgLevel(String boundedOrgLevel) {
		this.boundedOrgLevel = boundedOrgLevel;
	}

	
}
