package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 系统EBS库存备份
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_EBS_INVENTORY")
public class EbsInventory  extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8748650167356859831L;
	private Long id;
	private Date createTime;
	private Long skuId;
	private Long transQty;
	private Long customerId;
	private String customerCode;
	private String customerSkuCode;
	private Long totalQty;
	private String batchCode;
	private String channelCode;
	
	@Id
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="SKU_ID")
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	@Column(name="TRANS_QTY")
	public Long getTransQty() {
		return transQty;
	}
	public void setTransQty(Long transQty) {
		this.transQty = transQty;
	}
	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@Column(name="CUSTOMER_CODE")
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	@Column(name="CUSTOMER_SKU_CODE")
	public String getCustomerSkuCode() {
		return customerSkuCode;
	}
	public void setCustomerSkuCode(String customerSkuCode) {
		this.customerSkuCode = customerSkuCode;
	}
	@Column(name="TOTAL_QTY")
	public Long getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}
	@Column(name="BATCH_CODE")
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	@Column(name="CHANNEL_CODE")
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	
}
