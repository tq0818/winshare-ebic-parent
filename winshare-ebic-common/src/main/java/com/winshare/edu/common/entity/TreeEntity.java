package com.winshare.edu.common.entity;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 树形抽象实体类
 *
 */
public abstract class TreeEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	protected T parent;	// 父级编号
	protected String parentIds; // 所有父级编号
	
	public TreeEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public TreeEntity(Long id) {
		super(id);
	}
	
	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 * @return
	 */
	public abstract T getParent();

	/**
	 * 父对象，只能通过子类实现，父类实现mybatis无法读取
	 * @return
	 */
	public abstract void setParent(T parent);

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public Long getParentId() {
		Long id = null;
		if (parent != null){
			try {
				id = (Long) PropertyUtils.getProperty(parent, "id");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id != null ? id : 0;
	}
	
}
