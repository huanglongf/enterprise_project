package com.jumbo.webservice.express;

import java.io.Serializable;

/**
 * 标准快递获取运单号实体
 * @author kai.zhu
 *
 */
public class ExpressGetMailNosRequest implements Serializable {
	
	private static final long serialVersionUID = 7651712394583267460L;
	
	private String account; 	// 快递供应商提供帐号信息
	private String checkword;	// 快递供应商提供帐号对应验证码
	private String regionCode;	// 区域编码
	private String batchId;		// 批次号，系统唯一用于标识此次请求ID
	private Integer qty;		// 请求运单数量
	private String lpCode;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCheckword() {
		return checkword;
	}
	public void setCheckword(String checkword) {
		this.checkword = checkword;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getLpCode() {
		return lpCode;
	}
	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}
	
	
}
