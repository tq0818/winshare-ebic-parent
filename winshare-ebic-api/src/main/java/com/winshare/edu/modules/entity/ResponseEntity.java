package com.winshare.edu.modules.entity;

import com.winshare.edu.common.persistence.BaseEntity;

public class ResponseEntity extends BaseEntity<ResponseEntity>{
	
	private static final long serialVersionUID = -7968628492247518298L;

	private String statusCode;
	
	private String exceptionCode;
	
	private String message;
	
	private Object content;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
	
}
