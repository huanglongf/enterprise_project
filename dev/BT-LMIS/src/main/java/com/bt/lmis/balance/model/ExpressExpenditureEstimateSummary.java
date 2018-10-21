package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @ClassName: ExpressExpenditureEstimateSummary
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月10日 上午9:52:21 
 * 
 */
public class ExpressExpenditureEstimateSummary implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 2111334893697398966L;
	private String id;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private String batchNumber;
	private int contractId;
	private String express;
	private String costCenter;
	private String storeCode;
	private String storeName;
	private String warehouseName;
	private int orderNum;
	private String productTypeCode;
	private String productTypeName;
	private BigDecimal productTypeFreight;
	private BigDecimal productTypeDiscount;
	private BigDecimal productTypeTotalDiscount;
	private BigDecimal insurance;
	private BigDecimal totalFee;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public BigDecimal getProductTypeFreight() {
		return productTypeFreight;
	}
	public void setProductTypeFreight(BigDecimal productTypeFreight) {
		this.productTypeFreight = productTypeFreight;
	}
	public BigDecimal getProductTypeDiscount() {
		return productTypeDiscount;
	}
	public void setProductTypeDiscount(BigDecimal productTypeDiscount) {
		this.productTypeDiscount = productTypeDiscount;
	}
	public BigDecimal getProductTypeTotalDiscount() {
		return productTypeTotalDiscount;
	}
	public void setProductTypeTotalDiscount(BigDecimal productTypeTotalDiscount) {
		this.productTypeTotalDiscount = productTypeTotalDiscount;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
