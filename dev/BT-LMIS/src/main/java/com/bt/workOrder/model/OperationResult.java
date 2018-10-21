package com.bt.workOrder.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class OperationResult {
	// 操作结果
	private Boolean result;
	// 操作结果内容
	private String resultContent;
	// 回参
	private Map<String, Object> returnMap;
	//
	public OperationResult() {}
	public OperationResult(Boolean result) {
		this.result = result;
		returnMap = new LinkedHashMap<String, Object>();
		
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	public Map<String, Object> getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

}