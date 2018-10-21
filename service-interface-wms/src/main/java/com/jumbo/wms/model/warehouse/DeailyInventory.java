package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 仓库日库存报表
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_DEAILY_INVENTORY")
public class DeailyInventory extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6488126601128559782L;
	private Long ID;
	private Long whouId;
	private String whouCode;
	private Date createTime;
	private Long skuId;
	private String customerSkuCode;
	private Long customerId;
	private String customerCode;
	private String channelCode;
	private Long salesAvailQty;
	private Integer status;
	
	@Id
	@Column(name="id")
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	@Column(name="wh_ou_id")
	public Long getWhouId() {
		return whouId;
	}
	public void setWhouId(Long whouId) {
		this.whouId = whouId;
	}
	@Column(name="WH_OU_CODE")
	public String getWhouCode() {
		return whouCode;
	}
	public void setWhouCode(String whouCode) {
		this.whouCode = whouCode;
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
	@Column(name="CUSTOMER_SKU_CODE")
	public String getCustomerSkuCode() {
		return customerSkuCode;
	}
	public void setCustomerSkuCode(String customerSkuCode) {
		this.customerSkuCode = customerSkuCode;
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
	@Column(name="CHANNEL_CODE")
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	@Column(name="SALES_AVAIL_QTY")
	public Long getSalesAvailQty() {
		return salesAvailQty;
	}
	public void setSalesAvailQty(Long salesAvailQty) {
		this.salesAvailQty = salesAvailQty;
	}
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	


}
