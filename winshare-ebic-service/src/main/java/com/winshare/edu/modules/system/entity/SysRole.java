package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.BaseEntity;

public class SysRole extends BaseEntity<SysRole>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455382492350818774L;

	private String roleName;
	
	private String roleIden;
	
	private String remarks;
	
	private Integer status;
	
			
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleIden() {
		return roleIden;
	}

	public void setRoleIden(String roleIden) {
		this.roleIden = roleIden;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

}
