package com.jumbo.webservice.rfd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RfdCommonResponse {
	
	@JsonProperty(value = "IsSuccess")
	protected Boolean isSuccess;	// 是否通讯成功
	@JsonProperty(value = "Message")
	protected String message;		// 结果信息
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
