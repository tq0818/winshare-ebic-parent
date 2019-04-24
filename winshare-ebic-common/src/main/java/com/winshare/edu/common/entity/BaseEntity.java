package com.winshare.edu.common.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @描述: 基础实体类，包含各实体公用属性 .
 */
public abstract class BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = -5875361369845226068L;

	/** 主键ID **/
	private Long id;

	/** 创建时间 **/
	private Date createTime;

	/** 修改时间 **/
	private Date modifyTime;
	
	public BaseEntity() {
		
	}
	
	public BaseEntity(Long id) {
		this();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	
}
