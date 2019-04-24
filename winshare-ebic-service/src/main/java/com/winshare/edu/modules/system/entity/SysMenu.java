package com.winshare.edu.modules.system.entity;

import java.util.List;

import com.winshare.edu.common.entity.TreeEntity;

public class SysMenu extends TreeEntity<SysMenu>{

	private static final long serialVersionUID = 1L;
	
	private String menuName;
	
	private String menuIden;
	
	private String menuIcon;
	
	private Integer menuSort;
	
	private String menuType;
	
	private String menuUrl;
	
	private String displayStatus;
	
	private String remarks;

	private List<SysMenu> children;	// 父级菜单

	public SysMenu(){
		super();
		this.menuSort = 30;
		this.displayStatus = "1";
	}
	
	public SysMenu(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SysMenu getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(SysMenu parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIden() {
		return menuIden;
	}

	public void setMenuIden(String menuIden) {
		this.menuIden = menuIden;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Long getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : 0;
	}

}
