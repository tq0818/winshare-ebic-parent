package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.TreeEntity;

public class SysDic extends TreeEntity<SysDic> implements Comparable<SysDic>{

	private static final long serialVersionUID = -4475252110259878045L;

	private String dicName;
	
	private String dicCode;
	
	private String dicValue;
	
	private Integer dicSort;

	private String typeName;

	private String typeCode;

	private String typeValue;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public SysDic(){
		super();
		this.dicSort = 30;
	}
	
	public SysDic(Long id) {
		super(id);
	}
	
	@Override
	public SysDic getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(SysDic parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public Integer getDicSort() {
		return dicSort;
	}

	public void setDicSort(Integer dicSort) {
		this.dicSort = dicSort;
	}
	
	public Long getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : 0;
	}

	@Override
	public int compareTo(SysDic o) {
		// TODO Auto-generated method stub
		return this.getDicSort() - o.getDicSort();
	}
	
}
