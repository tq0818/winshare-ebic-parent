package com.winshare.edu.common.entity;


public class StudentVo extends BaseEntity<SubjectVo>{

	private static final long serialVersionUID = -5694348626391357549L;

	private Long id;
	
	private String account;
	
	private String loginAlias;
	
	private String studentName;
	
	private String sex;
	
	private String userAvatar;

	private String cardNum;

	private Integer studentScore;

	private Integer studentId;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoginAlias() {
		return loginAlias;
	}

	public void setLoginAlias(String loginAlias) {
		this.loginAlias = loginAlias;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Integer getStudentScore() {
		return studentScore;
	}

	public void setStudentScore(Integer studentScore) {
		this.studentScore = studentScore;
	}
}
