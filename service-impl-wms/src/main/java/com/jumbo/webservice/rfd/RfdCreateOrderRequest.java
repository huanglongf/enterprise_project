package com.jumbo.webservice.rfd;

import java.math.BigDecimal;
import java.util.List;
/**
 * 2.1	订单导入
 */
public class RfdCreateOrderRequest {
	
	private String merchantCode;		// 商家编号
	private String formCode;			// 商家订单号
	private String perFormCode;			// 前订单号，用作退货和换货单之前的订单号
	private Long waybillNo;				// 运单号
	private String mainCode;			// 商家主单号
	private String deliverCode;			// 商家运单号
	private String toName;				// 收货人姓名
	private String toAddress;			// 收货人地址
	private String toProvince;			// 收货人省份
	private String toCity;				// 收货人城市
	private String toArea;				// 收货人地区
	private String toPostCode;			// 收货人邮编
	private String toMobile;			// 收货人手机号码
	private String toPhone;				// 收货人电话号码
	private Integer orderType;			// 订单类型
	private BigDecimal totalAmount;		// 总金额
	private BigDecimal paidAmount;		// 已付金额
	private BigDecimal receiveAmount;	// 应收金额
	private BigDecimal refundAmount;	// 应退金额
	private BigDecimal insureAmount;	// 保价金额
	private BigDecimal weight;			// 重量
	private String comment;				// 重要提示
	private String fromName;			// 发货用户姓名
	private String fromAddress;			// 发件地址
	private String fromProvince;		// 发件省
	private String fromCity;			// 发件市
	private String fromArea;			// 发件区
	private String fromPostCode;		// 发件邮编
	private String fromMobile;			// 发件人手机号码
	private String fromPhone;			// 发件人电话号码
	private Integer goodsProperty;		// 货物属性
	private String goodsCategory;		// 货物品类
	private Integer packageCount;		// 总件数
	private String distributionCode;	// 接单配送商（分单）
	private String currentDistributioncode; // 当前接单配送商
	private String wareHouseId;			// 仓库ID（分单）
	private String warehouseName;		// 仓库名称
	private String sortingCenterId;		// 分拣中心ID（分单）
	private String sortingCenterName;	// 分拣中心名称
	private Integer cashType;			// 支付方式
	private String extendData;			// 拓展字段（备用）
	private String sendTimeType;		// 配送方式
	private String deliveryStartDate;	// 预约配送开始时间
	private String deliveryEndDate;		// 预约配送结束时间
	private List<RfdCreateOrderDetails> orderDetails; // 订单明细
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getPerFormCode() {
		return perFormCode;
	}

	public void setPerFormCode(String perFormCode) {
		this.perFormCode = perFormCode;
	}

	public Long getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(Long waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getMainCode() {
		return mainCode;
	}

	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}

	public String getDeliverCode() {
		return deliverCode;
	}

	public void setDeliverCode(String deliverCode) {
		this.deliverCode = deliverCode;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToProvince() {
		return toProvince;
	}

	public void setToProvince(String toProvince) {
		this.toProvince = toProvince;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToArea() {
		return toArea;
	}

	public void setToArea(String toArea) {
		this.toArea = toArea;
	}

	public String getToPostCode() {
		return toPostCode;
	}

	public void setToPostCode(String toPostCode) {
		this.toPostCode = toPostCode;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getInsureAmount() {
		return insureAmount;
	}

	public void setInsureAmount(BigDecimal insureAmount) {
		this.insureAmount = insureAmount;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromProvince() {
		return fromProvince;
	}

	public void setFromProvince(String fromProvince) {
		this.fromProvince = fromProvince;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromArea() {
		return fromArea;
	}

	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}

	public String getFromPostCode() {
		return fromPostCode;
	}

	public void setFromPostCode(String fromPostCode) {
		this.fromPostCode = fromPostCode;
	}

	public String getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(String fromMobile) {
		this.fromMobile = fromMobile;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public Integer getGoodsProperty() {
		return goodsProperty;
	}

	public void setGoodsProperty(Integer goodsProperty) {
		this.goodsProperty = goodsProperty;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public Integer getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}

	public String getDistributionCode() {
		return distributionCode;
	}

	public void setDistributionCode(String distributionCode) {
		this.distributionCode = distributionCode;
	}

	public String getCurrentDistributioncode() {
		return currentDistributioncode;
	}

	public void setCurrentDistributioncode(String currentDistributioncode) {
		this.currentDistributioncode = currentDistributioncode;
	}

	public String getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSortingCenterId() {
		return sortingCenterId;
	}

	public void setSortingCenterId(String sortingCenterId) {
		this.sortingCenterId = sortingCenterId;
	}

	public String getSortingCenterName() {
		return sortingCenterName;
	}

	public void setSortingCenterName(String sortingCenterName) {
		this.sortingCenterName = sortingCenterName;
	}

	public Integer getCashType() {
		return cashType;
	}

	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

	public String getExtendData() {
		return extendData;
	}

	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}

	public String getSendTimeType() {
		return sendTimeType;
	}

	public void setSendTimeType(String sendTimeType) {
		this.sendTimeType = sendTimeType;
	}

	public String getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(String deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public String getDeliveryEndDate() {
		return deliveryEndDate;
	}

	public void setDeliveryEndDate(String deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}

	public List<RfdCreateOrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<RfdCreateOrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
