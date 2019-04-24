package com.winshare.edu.modules.entity.parameter;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class T100_V1_0{

	@NotNull(message = "param属性不能为空")
	@Length(min=1, max=60, message="param属性长度必须介于 1 和 60之间")
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
}
