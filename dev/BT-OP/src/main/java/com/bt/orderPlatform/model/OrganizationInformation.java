package com.bt.orderPlatform.model;

/**
* @ClassName: OrganizationInformation
* @Description: TODO(OrganizationInformation)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class OrganizationInformation extends Jurisdiction{
	
	
	private  String custid;   //月结卡号
	private  String secret_key;   //密钥
	private  String pay_path;   //支付方式
	private  String store_code;   //店铺code
	
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public String getPay_path() {
		return pay_path;
	}
	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getOrg_province_code() {
		return org_province_code;
	}
	public void setOrg_province_code(String org_province_code) {
		this.org_province_code = org_province_code;
	}
	public String getOrg_city_code() {
		return org_city_code;
	}
	public void setOrg_city_code(String org_city_code) {
		this.org_city_code = org_city_code;
	}
	public String getOrg_state_code() {
		return org_state_code;
	}
	public void setOrg_state_code(String org_state_code) {
		this.org_state_code = org_state_code;
	}
	public String getId() {
}