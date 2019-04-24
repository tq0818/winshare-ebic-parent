package com.winshare.edu.modules.entity.parameter;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Q10002_V1_0 {

	@NotNull(message = "班级ID不能为空")
	private Long classId;

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "科目不能为空")
	private String subject;

	@NotNull(message = "年级不能为空")
	private String grade;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
