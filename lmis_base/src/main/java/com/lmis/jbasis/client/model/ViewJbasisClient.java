package com.lmis.jbasis.client.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewJbasisClient
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 */
public class ViewJbasisClient {
	
	@ApiModelProperty(value = "主键")
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ApiModelProperty(value = "创建人")
	private String createBy;
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	@ApiModelProperty(value = "是否删除")
	private Boolean isDeleted;
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@ApiModelProperty(value = "启停标志")
	private Boolean isDisabled;
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	@ApiModelProperty(value = "")
	private String isDisabledDisplay;
	public String getIsDisabledDisplay() {
		return isDisabledDisplay;
	}
	public void setIsDisabledDisplay(String isDisabledDisplay) {
		this.isDisabledDisplay = isDisabledDisplay;
	}
	
	@ApiModelProperty(value = "版本号")
	private Integer version;
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@ApiModelProperty(value = "所属机构")
	private String pwrOrg;
	public String getPwrOrg() {
		return pwrOrg;
	}
	public void setPwrOrg(String pwrOrg) {
		this.pwrOrg = pwrOrg;
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
	
	@ApiModelProperty(value = "")
	private String clientTypeDisplay;
	public String getClientTypeDisplay() {
		return clientTypeDisplay;
	}
	public void setClientTypeDisplay(String clientTypeDisplay) {
		this.clientTypeDisplay = clientTypeDisplay;
	}
	
	@ApiModelProperty(value = "结算方式(0:代销；1：结算经销；2：付款经销)")
	private String settlementMethod;
	public String getSettlementMethod() {
		return settlementMethod;
	}
	public void setSettlementMethod(String settlementMethod) {
		this.settlementMethod = settlementMethod;
	}
	
	@ApiModelProperty(value = "")
	private String settlementMethodDisplay;
	public String getSettlementMethodDisplay() {
		return settlementMethodDisplay;
	}
	public void setSettlementMethodDisplay(String settlementMethodDisplay) {
		this.settlementMethodDisplay = settlementMethodDisplay;
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
	
	@ApiModelProperty(value = "")
	private String invoiceTypeDisplay;
	public String getInvoiceTypeDisplay() {
		return invoiceTypeDisplay;
	}
	public void setInvoiceTypeDisplay(String invoiceTypeDisplay) {
		this.invoiceTypeDisplay = invoiceTypeDisplay;
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
	
	@ApiModelProperty(value = "")
	private String validityDisplay;
	public String getValidityDisplay() {
		return validityDisplay;
	}
	public void setValidityDisplay(String validityDisplay) {
		this.validityDisplay = validityDisplay;
	}
	
}
