package com.winshare.edu.modules.system.entity;

import java.io.Serializable;

public class SysOperation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7145190890705078646L;

	private Long id;
	
	private Long menuId;
	
	private String operationName;
	
	private String operationIden;
	
	private Long roleRelaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationIden() {
		return operationIden;
	}

	public void setOperationIden(String operationIden) {
		this.operationIden = operationIden;
	}

	public Long getRoleRelaId() {
		return roleRelaId;
	}

	public void setRoleRelaId(Long roleRelaId) {
		this.roleRelaId = roleRelaId;
	}
	
	
	
}
