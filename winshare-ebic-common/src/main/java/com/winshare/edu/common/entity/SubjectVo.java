package com.winshare.edu.common.entity;



public class SubjectVo extends BaseEntity<SubjectVo>{

	private static final long serialVersionUID = -8743177474522322020L;

	private Long id;
	
	private String subjectName;

	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	
}
