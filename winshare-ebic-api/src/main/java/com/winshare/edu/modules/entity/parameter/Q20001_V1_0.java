package com.winshare.edu.modules.entity.parameter;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Q20001_V1_0 {
	@NotNull(message = "账号不能为空")
	@Length(max=100, message="账号长度必须小于100")
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
