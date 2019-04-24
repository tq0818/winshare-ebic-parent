package com.winshare.edu.modules.system.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;

public class OrgPowerLog extends BaseEntity<OrgPower>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 264151888590959568L;


    private Long orgId;
	
	private String contractNumber;
	
	private Date endTime;
	
	private Integer teacherCount;
	
	private Integer teacherFree;
	
	private Integer studentCount;
	
	private Integer studentFree;
	
	private String remarks;
	
	private Long accountId;
	
	private String orgName;
	
	private String creatorName;
	
	private Long updateAccountId;
	
	private String updateAccountName;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(Integer teacherCount) {
		this.teacherCount = teacherCount;
	}

	public Integer getTeacherFree() {
		return teacherFree;
	}

	public void setTeacherFree(Integer teacherFree) {
		this.teacherFree = teacherFree;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public Integer getStudentFree() {
		return studentFree;
	}

	public void setStudentFree(Integer studentFree) {
		this.studentFree = studentFree;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getUpdateAccountId() {
		return updateAccountId;
	}

	public void setUpdateAccountId(Long updateAccountId) {
		this.updateAccountId = updateAccountId;
	}

	public String getUpdateAccountName() {
		return updateAccountName;
	}

	public void setUpdateAccountName(String updateAccountName) {
		this.updateAccountName = updateAccountName;
	}
	
	public String getState(){
		String result = "禁用";
		if(this.endTime != null && this.endTime.getTime() >= new Date().getTime()){
			result = "启用";
		}
		return result;
	}
	
}
