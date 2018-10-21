package com.bt.workOrder.model;

/**
 * @Title:VerificationResult
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年2月22日下午11:32:46
 */
public class VerificationResult {
	
	/**
	 * 
	 */
	private boolean flag;
	// 0-不需要记录日志，只需反馈用户得知
	// 1-需要记录日志，并且反馈用户得知
	// 2-需要记录日志，并且反馈用户得知，且系统介入帮助完成操作
	private Integer error_type;
	private String error_message;
	public VerificationResult() {
		
	}
	public VerificationResult(boolean flag) {
		this.flag = flag;
		
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getError_type() {
		return error_type;
	}
	public void setError_type(Integer error_type) {
		this.error_type = error_type;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
}
