package com.winshare.edu.common.entity;

import java.util.List;

public class TeacherVo extends BaseEntity<TeacherVo>{

	private static final long serialVersionUID = -4409590622654389237L;

	private Long orgId;
	
	private String orgName;
	
	private String sex;

	private Integer teacherId;

	private String teacherName;

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	private List<SubjectVo> subjectList;
	
	private List<ClassVo> classList;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<SubjectVo> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectVo> subjectList) {
		this.subjectList = subjectList;
	}

	public List<ClassVo> getClassList() {
		return classList;
	}

	public void setClassList(List<ClassVo> classList) {
		this.classList = classList;
	}
	
	

}
