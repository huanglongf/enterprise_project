package com.bt.common.model;

import java.io.Serializable;

/** 
 * @ClassName: ResultMessage
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月1日 下午3:21:38 
 * 
 */
public class ResultMessage implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3863066562071652333L;
	
	// 正常
	public static final String SUCCESS="200";
	// 系统异常
	public static final String SYSTEM_ERR="300";
	// 操作异常
	public static final String OPERATION_ERROR="400";
	
	private String code;
	private String message;
	public ResultMessage() {}
	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getSuccess() {
		return SUCCESS;
	}
	public static String getSystemErr() {
		return SYSTEM_ERR;
	}
	public static String getOperationError() {
		return OPERATION_ERROR;
	}

}
