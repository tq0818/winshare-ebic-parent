package com.winshare.edu.modules.entity.parameter;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Q10003_V1_0 {

	@NotNull(message = "类型不能为空")
	@Length(min=4, max=30, message="类型长度必须介于 4和 30之间")
	private String typeCode;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
