package com.winshare.edu.modules.system.entity;

import com.winshare.edu.common.entity.TreeEntity;

/** 
  * @author  hujie 
  * @date 创建时间：2017年9月27日 上午11:13:54  
  * @parameter  
  * @since  带班级的机构树实体
  * @return  
*/
public class TreeInfo extends TreeEntity<TreeInfo>{

	private static final long serialVersionUID = -22684023482243222L;
	
	private String orgName;//机构名称
	
	private Long parentId;//上级机构id
	
	private Long cId;//作为班级的班级id
	
	private int isClass;//是否是班级（叶子节点 0:机构 1:班级）
	
	
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
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public TreeInfo getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(TreeInfo parent) {
		// TODO Auto-generated method stub
		
	}

}
