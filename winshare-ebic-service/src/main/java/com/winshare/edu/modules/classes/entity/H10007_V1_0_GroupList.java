package com.winshare.edu.modules.classes.entity;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class H10007_V1_0_GroupList {

	private Long groupId;

	@NotNull(message = "组长ID不能为空")
	private Long leaderId;

	@NotNull(message = "组名不能为空")
	@Length(max=100, message="组名长度必须小于100")
	private String groupName;

	private Long[] students;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long[] getStudents() {
		return students;
	}

	public void setStudents(Long[] students) {
		this.students = students;
	}
}
