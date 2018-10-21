package com.bt.workOrder.controller.param;

import com.bt.common.controller.param.CommonParam;

/**
 * @Title:OMSInterfaceExcpeitonHandlingParam
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年4月10日下午5:24:56
 */
public class OMSInterfaceExcpeitonHandlingParam extends CommonParam {
	private String claimCode;
	private String systemCode;
	private String omsOrderCode;
	private String erpOrderCode;
	private String rasCode;
	private String transNumber;
	private String claim_flag;
	private String feedback_flag;
	
	public String getClaim_flag() {
		return claim_flag;
	}
	public void setClaim_flag(String claim_flag) {
		this.claim_flag = claim_flag;
	}
	public String getFeedback_flag() {
		return feedback_flag;
	}
	public void setFeedback_flag(String feedback_flag) {
		this.feedback_flag = feedback_flag;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getOmsOrderCode() {
		return omsOrderCode;
	}
	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}
	public String getErpOrderCode() {
		return erpOrderCode;
	}
	public void setErpOrderCode(String erpOrderCode) {
		this.erpOrderCode = erpOrderCode;
	}
	public String getRasCode() {
		return rasCode;
	}
	public void setRasCode(String rasCode) {
		this.rasCode = rasCode;
	}
	public String getTransNumber() {
		return transNumber;
	}
	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}
	
}
