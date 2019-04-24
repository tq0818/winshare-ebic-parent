package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.TreeEntity;

public class AreaInfo extends TreeEntity<AreaInfo>{

	
	private String name;
	
	private String fullName;
	
	private String code;
	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6881909552390991378L;

	@Override
	public AreaInfo getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(AreaInfo parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

}
