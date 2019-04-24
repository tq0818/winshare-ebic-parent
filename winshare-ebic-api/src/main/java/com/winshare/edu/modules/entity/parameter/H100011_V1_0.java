package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class H100011_V1_0 {

	@NotNull(message = "学生ID不能为空")
	private Long classId;

	@NotNull(message = "教师ID不能为空")
	private Long teacherId;

	@NotNull(message = "科目不能为空")
	@Length(max=8, message="科目长度必须小于8")
	private String subject;

	@NotNull(message = "教师姓名不能为空")
	@Length(max=100, message="教师姓名长度必须小于100")
	private String teacherName;

	@NotNull(message = "教师端协议不能为空")
	@Length(max=20, message="教师端协议长度必须小于20")
	private String teacherProtocol;

	@NotNull(message = "教师端IP不能为空")
	@Length(max=50, message="教师端IP长度必须小于50")
	private String teacherIp;

	@NotNull(message = "教师端端口不能为空")
	@Length(max=10, message="教师端端口长度必须小于10")
	private String teacherPort;

	@NotNull(message = "年级名称不能为空")
	@Length(max=100, message="年级名称长度必须小于100")
	private String gradeName;

	@NotNull(message = "科目名称不能为空")
	@Length(max=100, message="科目名称长度必须小于100")
	private String subjectName;

	@NotNull(message = "书籍名称不能为空")
	@Length(max=100, message="书籍名称长度必须小于100")
	private String bookName;

	@NotNull(message = "章节名称不能为空")
	@Length(max=100, message="章节名称长度必须小于100")
	private String chapterName;


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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherProtocol() {
		return teacherProtocol;
	}

	public void setTeacherProtocol(String teacherProtocol) {
		this.teacherProtocol = teacherProtocol;
	}

	public String getTeacherIp() {
		return teacherIp;
	}

	public void setTeacherIp(String teacherIp) {
		this.teacherIp = teacherIp;
	}

	public String getTeacherPort() {
		return teacherPort;
	}

	public void setTeacherPort(String teacherPort) {
		this.teacherPort = teacherPort;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
