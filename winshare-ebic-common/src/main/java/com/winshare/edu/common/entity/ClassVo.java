package com.winshare.edu.common.entity;


public class ClassVo extends BaseEntity<TeacherVo>{

	private static final long serialVersionUID = 8604095019616868868L;

	private Long id;
	
	private String className;

	private Long classId;

	private String product;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
