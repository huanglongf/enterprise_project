package com.jumbo.util;

import java.io.Serializable;

public class HttpResult implements Serializable{
	
	/**
	 * Administrator
	 * 
	 */
	private static final long serialVersionUID = -834875450915722728L;

	/** 是否成功. */
	private boolean isSuccess;

	/**
	 * 错误码,某些时候需要 设置 ,而前台判断(可选)
	 */
	private Integer errorCode = null;

	/** 描述. */
	private String	description;
	
	public HttpResult() {
		super();
	}
	
	public HttpResult(boolean isSuccess, String description) {
		super();
		this.isSuccess = isSuccess;
		this.description = description;
	}

	public HttpResult(boolean isSuccess, Integer errorCode) {
		super();
		this.isSuccess = isSuccess;
		this.errorCode = errorCode;
	}

	public HttpResult(boolean isSuccess, Integer errorCode, String description) {
		super();
		this.isSuccess = isSuccess;
		this.errorCode = errorCode;
		this.description = description;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
