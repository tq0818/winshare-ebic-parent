package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Q100016_V1_0 {

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "班级ID不能为空")
	private Long classId;

	@NotNull(message = "科目不能为空")
	@Length(max=8, message="科目长度必须小于8")
	private String subject;

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
