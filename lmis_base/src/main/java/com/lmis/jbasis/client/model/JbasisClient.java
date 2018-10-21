package com.lmis.jbasis.client.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: JbasisClient
 * @Description: TODO(客户信息(基础数据))
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 */
public class JbasisClient extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "客户编码")
	private String clientCode;
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
    @ApiModelProperty(value = "客户名称")
	private String clientName;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
    @ApiModelProperty(value = "客户类型:（3：其他;2：重要客户;1：普通客户;0：一般客户)")
	private String clientType;
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
    @ApiModelProperty(value = "结算方式(0:代销；1：结算经销；2：付款经销)")
	private String settlementMethod;
	public String getSettlementMethod() {
		return settlementMethod;
	}
	public void setSettlementMethod(String settlementMethod) {
		this.settlementMethod = settlementMethod;
	}
	
    @ApiModelProperty(value = "联系人")
	private String contact;
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
    @ApiModelProperty(value = "联系电话")
	private String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
    @ApiModelProperty(value = "联系地址")
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
    @ApiModelProperty(value = "开票公司")
	private String company;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
    @ApiModelProperty(value = "发票类型(0:手写发票；1：机打发票，2：普通，3：增值税发票，4：其他)")
	private String invoiceType;
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
    @ApiModelProperty(value = "发票信息")
	private String invoiceInfo;
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	
    @ApiModelProperty(value = "备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    @ApiModelProperty(value = "有效性(2:有效；3：无效；)")
	private String validity;
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
}
