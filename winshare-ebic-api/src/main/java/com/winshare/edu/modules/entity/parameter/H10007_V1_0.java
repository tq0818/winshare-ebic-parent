package com.winshare.edu.modules.entity.parameter;


import com.winshare.edu.modules.classes.entity.H10007_V1_0_GroupList;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class H10007_V1_0 {

	@NotNull(message = "班级ID不能为空")
	private Long classId;

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "科目不能为空")
	@Length(max=8, message="科目长度必须小于8")
	private String subject;

	@NotNull(message = "年级不能为空")
	@Length(max=8, message="年级长度必须小于8")
	private String grade;

	private List<H10007_V1_0_GroupList> groupList;



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

	public List<H10007_V1_0_GroupList> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<H10007_V1_0_GroupList> groupList) {
		this.groupList = groupList;
	}
}
