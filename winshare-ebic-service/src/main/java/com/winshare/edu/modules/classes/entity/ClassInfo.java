package com.winshare.edu.modules.classes.entity;

import com.winshare.edu.common.entity.BaseEntity;
import com.winshare.edu.modules.system.entity.OrgInfo;

public class ClassInfo extends BaseEntity<ClassInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -323559430869277831L;

	private Long orgId;
	
	private String studySection;//学段
	
	private String classYear;
	
	private String className;
	
	private String classCode;
	
	private String classStatus;
	
	private String remarks;
	
	private OrgInfo orgInfo;
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getStudySection() {
		return studySection;
	}

	public void setStudySection(String studySection) {
		this.studySection = studySection;
	}

	public String getClassYear() {
		return classYear;
	}

	public void setClassYear(String classYear) {
		this.classYear = classYear;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public OrgInfo getOrgInfo() {
		return orgInfo;
	}

	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}
	
}
