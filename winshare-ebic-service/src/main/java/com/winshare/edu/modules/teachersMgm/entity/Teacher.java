package com.winshare.edu.modules.teachersMgm.entity;

import java.util.Date;

import com.winshare.edu.common.entity.BaseEntity;
import com.winshare.edu.modules.system.entity.AccountInfo;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月18日 下午3:41:29  
  * @parameter  
  * @since  教师实体类
  * @return  
*/
public class Teacher extends BaseEntity<Teacher>{

	private static final long serialVersionUID = -8957313039310395699L;
	
	private Long userId;//教师id
	
	private String account;//账号
	
	private Long accountId;//账号id
	
	private String loginName;//登录名
	
	private String name;//姓名
	
	private String orgName;//机构名称(所属学校)
	
	private String accStatus;//账号状态
	
	private String areaCode;//区域
	
	private String sex;//性别
	
	private String birDate;//出生日期
	
	private Date birthDate;
	
	private String phone;//手机
	
	private String email;//电邮
	
	private Long orgId;//所属机构id
	
	private String subjectId;//学科id
	
	private String subjectName;//学科名称
	
	private String password;
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirDate() {
		return birDate;
	}
	public void setBirDate(String birDate) {
		this.birDate = birDate;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAccStatus() {
		return accStatus;
	}
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
