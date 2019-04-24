package com.winshare.edu.modules.system.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;

public class OrgPower  extends BaseEntity<OrgPower>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 40338038363909242L;
	
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
	
	private Integer status;
	
	
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

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

	public String getState(){
		String result = "禁用";
		if(status != null && status.intValue() == 1){
			result = "启用";
		}
		return result;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
