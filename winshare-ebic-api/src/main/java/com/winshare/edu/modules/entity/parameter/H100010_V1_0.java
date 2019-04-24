package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class H100010_V1_0 {

	@NotNull(message = "学生ID不能为空")
	private Long studentId;

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "年级不能为空")
	@Length(max=8, message="年级长度必须小于8")
	private String grade;

	@NotNull(message = "科目不能为空")
	@Length(max=8, message="科目长度必须小于8")
	private String subject;

	@NotNull(message = "得分项不能为空")
	@Length(max=100, message="得分项长度必须小于100")
	private String itemName;

	private Integer score;

	@NotNull(message = "书籍名称不能为空")
	@Length(max=100, message="分组ID长度必须小于100")
	private String bookName;

	@NotNull(message = "章节名称不能为空")
	@Length(max=100, message="章节名称长度必须小于100")
	private String chapterName;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
}
