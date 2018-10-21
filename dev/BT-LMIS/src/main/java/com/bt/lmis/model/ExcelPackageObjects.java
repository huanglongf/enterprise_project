package com.bt.lmis.model;

import java.util.List;

public class ExcelPackageObjects {
	
	/**
	 * 封装成功/失败标识
	 */
	private Boolean result_flag;
	
	/**
	 * 失败原因
	 */
	private String reason;
	
	/**
	 * 封装结果
	 */
	private List<Object> result;

	public Boolean getResult_flag() {
		return result_flag;
	}

	public void setResult_flag(Boolean result_flag) {
		this.result_flag = result_flag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Object> getResult() {
		return result;
	}

	public void setResult(List<Object> result) {
		this.result = result;
	}

}
