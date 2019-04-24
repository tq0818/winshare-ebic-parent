package com.winshare.edu.modules.user.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;

public class StudentInfo extends BaseEntity<StudentInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096381623308684645L;

	private Long accountId;//账户id
	
	private String account;//帐号
	
	private String loginName;//登录名
	
	private Long orgId;//机构id
	
	private Long cId;//班级id
	
	private String orgName;//机构名称
	
	private String className;//班级名称

	private String classId;
	
	private String name;//姓名
	
	private String studentNo;//学生编号
	
	private String sex;//性别
	
	private Date birthDate;//出生日期
	
	private String province;//省
	
	private String city;//市
	
	private String area;//区
	
	private String areaCode;//区域编码
	
	private String userAvatar;//学生头像
	
	private String status;//帐户状态
	
	private int isClass;//是否是班级（叶子节点 0:机构 1:班级）
	
	private Long areaId;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public int getIsClass() {
		return isClass;
	}

	public void setIsClass(int isClass) {
		this.isClass = isClass;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
}
