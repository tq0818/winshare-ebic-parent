package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class H100013_V1_0 {

	@NotNull(message = "班级ID不能为空")
	private Long classId;

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "科目不能为空")
	@Length(max=8, message="科目长度必须小于8")
	private String subject;

	@NotNull(message = "条目名称不能为空")
	@Length(max=100, message="条目名称长度必须小于100")
	private String itemName;

	@NotNull(message = "分值不能为空")
	private Integer score;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
