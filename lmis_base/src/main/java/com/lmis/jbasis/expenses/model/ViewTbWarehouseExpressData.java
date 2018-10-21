package com.lmis.jbasis.expenses.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewTbWarehouseExpressData
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-04-24 10:45:54
 * 
 */
public class ViewTbWarehouseExpressData {
	
	@ApiModelProperty(value = "主键")
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ApiModelProperty(value = "批次号")
	private String batId;
	public String getBatId() {
		return batId;
	}
	public void setBatId(String batId) {
		this.batId = batId;
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
	private String createUser;
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	
	@ApiModelProperty(value = "成本中心编码")
	private String costCenter;
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	@ApiModelProperty(value = "更新人")
	private String updateUser;
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@ApiModelProperty(value = "店铺代码")
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
	
	@ApiModelProperty(value = "")
	private String warehouseCode;
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	@ApiModelProperty(value = "所属仓库")
	private String warehouse;
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@ApiModelProperty(value = "运输公司代码")
	private String transportCode;
	public String getTransportCode() {
		return transportCode;
	}
	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}
	
	@ApiModelProperty(value = "快递名称")
	private String transportName;
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	
	@ApiModelProperty(value = "发货指令/平台订单号")
	private String deliveryOrder;
	public String getDeliveryOrder() {
		return deliveryOrder;
	}
	public void setDeliveryOrder(String deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}
	
	@ApiModelProperty(value = "上位系统订单号/前置单据号/相关单据号")
	private String epistaticOrder;
	public String getEpistaticOrder() {
		return epistaticOrder;
	}
	public void setEpistaticOrder(String epistaticOrder) {
		this.epistaticOrder = epistaticOrder;
	}
	
	@ApiModelProperty(value = "订单类型")
	private String orderType;
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@ApiModelProperty(value = "运单号")
	private byte[] expressNumber;
	public byte[] getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(byte[] expressNumber) {
		this.expressNumber = expressNumber;
	}
	
	@ApiModelProperty(value = "运输方向(0:正向；1：逆向)")
	private String transportDirection;
	public String getTransportDirection() {
		return transportDirection;
	}
	public void setTransportDirection(String transportDirection) {
		this.transportDirection = transportDirection;
	}
	
	@ApiModelProperty(value = "产品类型代码")
	private String itemtypeCode;
	public String getItemtypeCode() {
		return itemtypeCode;
	}
	public void setItemtypeCode(String itemtypeCode) {
		this.itemtypeCode = itemtypeCode;
	}
	
	@ApiModelProperty(value = "产品类型名称")
	private String itemtypeName;
	public String getItemtypeName() {
		return itemtypeName;
	}
	public void setItemtypeName(String itemtypeName) {
		this.itemtypeName = itemtypeName;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "订单生成时间")
	private Date transportTime;
	public Date getTransportTime() {
		return transportTime;
	}
	public void setTransportTime(Date transportTime) {
		this.transportTime = transportTime;
	}
	
	@ApiModelProperty(value = "代收货款金额")
	private String collectionOnDelivery;
	public String getCollectionOnDelivery() {
		return collectionOnDelivery;
	}
	public void setCollectionOnDelivery(String collectionOnDelivery) {
		this.collectionOnDelivery = collectionOnDelivery;
	}
	
	@ApiModelProperty(value = "订单金额")
	private String orderAmount;
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@ApiModelProperty(value = "耗材SKU编码")
	private String skuNumber;
	public String getSkuNumber() {
		return skuNumber;
	}
	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}
	
	@ApiModelProperty(value = "品项数")
	private Integer qty;
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	@ApiModelProperty(value = "实际重量")
	private String weight;
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@ApiModelProperty(value = "长")
	private String length;
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	@ApiModelProperty(value = "宽")
	private String width;
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
	@ApiModelProperty(value = "高")
	private String higth;
	public String getHigth() {
		return higth;
	}
	public void setHigth(String higth) {
		this.higth = higth;
	}
	
	@ApiModelProperty(value = "体积")
	private String volumn;
	public String getVolumn() {
		return volumn;
	}
	public void setVolumn(String volumn) {
		this.volumn = volumn;
	}
	
	@ApiModelProperty(value = "始发地")
	private String deliveryAddress;
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	@ApiModelProperty(value = "省份")
	private String province;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@ApiModelProperty(value = "城市")
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@ApiModelProperty(value = "区县")
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@ApiModelProperty(value = "街道")
	private String street;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@ApiModelProperty(value = "")
	private String detailAddress;
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@ApiModelProperty(value = "收件人")
	private String shiptoname;
	public String getShiptoname() {
		return shiptoname;
	}
	public void setShiptoname(String shiptoname) {
		this.shiptoname = shiptoname;
	}
	
	@ApiModelProperty(value = "")
	private String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ApiModelProperty(value = "是否保价")
	private Boolean insuranceFlag;
	public Boolean getInsuranceFlag() {
		return insuranceFlag;
	}
	public void setInsuranceFlag(Boolean insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}
	
	@ApiModelProperty(value = "是否COD（0：是；1：否）")
	private Boolean codFlag;
	public Boolean getCodFlag() {
		return codFlag;
	}
	public void setCodFlag(Boolean codFlag) {
		this.codFlag = codFlag;
	}
	
	@ApiModelProperty(value = "结算主体")
	private String balanceSubject;
	public String getBalanceSubject() {
		return balanceSubject;
	}
	public void setBalanceSubject(String balanceSubject) {
		this.balanceSubject = balanceSubject;
	}
	
	@ApiModelProperty(value = "操作费是否结算	0 未结算 1已结算 2异常")
	private Integer operatingFlag;
	public Integer getOperatingFlag() {
		return operatingFlag;
	}
	public void setOperatingFlag(Integer operatingFlag) {
		this.operatingFlag = operatingFlag;
	}
	
	@ApiModelProperty(value = "")
	private String hcfSettleFlag;
	public String getHcfSettleFlag() {
		return hcfSettleFlag;
	}
	public void setHcfSettleFlag(String hcfSettleFlag) {
		this.hcfSettleFlag = hcfSettleFlag;
	}
	
	@ApiModelProperty(value = "报价表主键ID")
	private Integer offerId;
	public Integer getOfferId() {
		return offerId;
	}
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}
	
	@ApiModelProperty(value = "")
	private String kFlag;
	public String getKFlag() {
		return kFlag;
	}
	public void setKFlag(String kFlag) {
		this.kFlag = kFlag;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "")
	private Date platformOrderTime;
	public Date getPlatformOrderTime() {
		return platformOrderTime;
	}
	public void setPlatformOrderTime(Date platformOrderTime) {
		this.platformOrderTime = platformOrderTime;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "")
	private Date platformPayTime;
	public Date getPlatformPayTime() {
		return platformPayTime;
	}
	public void setPlatformPayTime(Date platformPayTime) {
		this.platformPayTime = platformPayTime;
	}
	
	@ApiModelProperty(value = "0 初始值  1可以备份")
	private String bfFlag;
	public String getBfFlag() {
		return bfFlag;
	}
	public void setBfFlag(String bfFlag) {
		this.bfFlag = bfFlag;
	}
	
	@ApiModelProperty(value = "所属客户")
	private String clientCode;
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
}
