package com.lmis.jbasis.store.model;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: ViewJbasisStore
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-04-13 10:22:24
 * 
 */
public class ViewJbasisStore {

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

	@ApiModelProperty(value = "成本中心")
	private String costCenter;

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	@ApiModelProperty(value = "店铺编码")
	private String storeCode;

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@ApiModelProperty(value = "店铺名称")
	private String storeName;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@ApiModelProperty(value = "所属客户")
	private String clientCode;

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	@ApiModelProperty(value = "")
	private String clientCodeDisplay;

	public String getClientCodeDisplay() {
		return clientCodeDisplay;
	}

	public void setClientCodeDisplay(String clientCodeDisplay) {
		this.clientCodeDisplay = clientCodeDisplay;
	}

	@ApiModelProperty(value = "店铺类型(0:A类；1：B类；2：其他)")
	private String storeType;

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@ApiModelProperty(value = "")
	private String storeTypeDisplay;

	public String getStoreTypeDisplay() {
		return storeTypeDisplay;
	}

	public void setStoreTypeDisplay(String storeTypeDisplay) {
		this.storeTypeDisplay = storeTypeDisplay;
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

	@ApiModelProperty(value = "发票类型(0:手写发票；1：机打发票，2：普通发票，3：增值税发票，4：其他)")
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

	@ApiModelProperty(value = "有效性")
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
