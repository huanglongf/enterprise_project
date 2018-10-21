package com.lmis.jbasis.store.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: JbasisStore
 * @Description: TODO(店铺信息（基础数据）)
 * @author codeGenerator
 * @date 2018-04-13 10:22:24
 * 
 */
public class JbasisStore extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "所属客户")
	private String clientCode;
    public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
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
	
    @ApiModelProperty(value = "店铺类型(0:A类；1：B类；2：其他)")
	private String storeType;
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
    @ApiModelProperty(value = "结算方式(0:代销；1：结算经销；2：付款经销)")
	private Integer settlementMethod;
	public Integer getSettlementMethod() {
		return settlementMethod;
	}
	public void setSettlementMethod(Integer settlementMethod) {
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
	
    @ApiModelProperty(value = "发票类型(0:手写发票；1：机打发票，2：普通发票，3：增值税发票，4：其他)")
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
	
    @ApiModelProperty(value = "有效性")
	private String validity;
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
    @ApiModelProperty(value = "0 nouse 1 use")
	private String woFlag;
	public String getWoFlag() {
		return woFlag;
	}
	public void setWoFlag(String woFlag) {
		this.woFlag = woFlag;
	}
	
}
