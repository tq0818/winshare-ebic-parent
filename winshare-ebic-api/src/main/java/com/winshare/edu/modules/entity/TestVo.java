package com.winshare.edu.modules.entity;

import com.winshare.edu.common.persistence.BaseEntity;

public class TestVo extends BaseEntity<TestVo>{

	private static final long serialVersionUID = -2798014934523991219L;
	
	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
