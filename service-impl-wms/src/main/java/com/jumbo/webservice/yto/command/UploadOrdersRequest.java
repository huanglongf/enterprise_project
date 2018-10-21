package com.jumbo.webservice.yto.command;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrder")
public class UploadOrdersRequest extends BaseModel{
	private static final long serialVersionUID = 184185286846566850L;

	/**
	 * 电商标识（K10101010）
	 */
	@XmlElement(required = true, name = "clientID")
	private String clientID;

	/**
	 * 物流公司ID
	 */
	@XmlElement(required = true, name = "logisticProviderID")
	private String logisticProviderID;

	/**
	 * 客户标识（如要有多个仓发货，请填写分仓号）
	 */
	@XmlElement(required = true, name = "customerId")
	private String customerId;

	/**
	 * 物流订单号
	 */
	@XmlElement(required = true, name = "txLogisticID")
	private String txLogisticID;

	/**
	 * 业务交易号（可选）
	 */
	@XmlElement(required = false, name = "tradeNo")
	private String tradeNo;

	/**
	 * 物流运单号
	 */
	@XmlElement(required = true, name = "mailNo")
	private String mailNo;

	/**
	 * 订单类型（该字段是为向下兼容预留）
	 */
	@XmlElement(required = true, name = "type")
	private int type;

	/**
	 * 订单类型(0-COD,1-普通订单,3-退货单)
	 */
	@XmlElement(required = true, name = "orderType")
	private int orderType;

	/**
	 * 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。默认为0
	 */
	@XmlElement(required = true, name = "serviceType")
	private long serviceType;

	/**
	 * 订单flag标识，便于以后分拣和标识默认为 0
	 */
	@XmlElement(required = false, name = "flag")
	private int flag;

	/**
	 * 发件人信息
	 */
	@XmlElement(required = true, name = "sender")
	private UploadOrdersSenderRequest sender;

	/**
	 * 收件人信息
	 */
	@XmlElement(required = true, name = "receiver")
	private UploadOrdersReceiverRequest receiver;

	/**
	 * 商品的
	 */
	@XmlElement(required = true, name = "items")
	private UploadOrdersItemsRequest items;


	/**
	 * 物流公司上门取货时间段，通过“yyyy-MM-dd HH:mm s.S z”格式化，本文中所有时间格式相同。
	 */
	@XmlElement(required = false, name = "sendStartTime")
	private Date sendStartTime;

	/**
	 * 
	 */
	@XmlElement(required = false, name = "sendEndTime")
	private Date sendEndTime;

	/**
	 * 商品金额，包括优惠和运费，但无服务费
	 */
	@XmlElement(required = false, name = "goodsValue")
	private Double goodsValue;

	/**
	 * goodsValue+总服务费
	 */
	@XmlElement(required = true, name = "totalValue")
	private Double totalValue;

	/**
	 * 代收金额，非代收设置为0.0
	 */
	@XmlElement(required = false, name = "itemsValue")
	private Double itemsValue;

	/**
	 * 代收金额
	 */
	@XmlElement(required = false, name = "agencyFund")
	private Double agencyFund;

	/**
	 * 货物总重量
	 */
	@XmlElement(required = false, name = "itemsWeight")
	private Double itemsWeight;

	/**
	 * 商品类型（保留字段，暂时不用）
	 */
	@XmlElement(required = false, name = "special")
	private int special;

	/**
	 * 备注
	 */
	@XmlElement(required = false, name = "remark")
	private String remark;

	/**
	 * 保值金额（暂时没有使用，默认为0.0）
	 */
	@XmlElement(required = true, name = "insuranceValue")
	private Double insuranceValue;

	/**
	 * 总服务费
	 */
	@XmlElement(required = true, name = "totalServiceFee")
	private Double totalServiceFee;

	/**
	 * 物流公司分润
	 */
	@XmlElement(required = true, name = "codSplitFee")
	private Double codSplitFee;
	
	/**
	 * 成功绑定的大头笔信息
	 */
	@XmlElement(required = true, name = "bigPen")
	private String bigPen;

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getLogisticProviderID() {
		return logisticProviderID;
	}

	public void setLogisticProviderID(String logisticProviderID) {
		this.logisticProviderID = logisticProviderID;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTxLogisticID() {
		return txLogisticID;
	}

	public void setTxLogisticID(String txLogisticID) {
		this.txLogisticID = txLogisticID;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public long getServiceType() {
		return serviceType;
	}

	public void setServiceType(long serviceType) {
		this.serviceType = serviceType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public UploadOrdersSenderRequest getSender() {
		return sender;
	}

	public void setSender(UploadOrdersSenderRequest sender) {
		this.sender = sender;
	}

	public UploadOrdersReceiverRequest getReceiver() {
		return receiver;
	}

	public void setReceiver(UploadOrdersReceiverRequest receiver) {
		this.receiver = receiver;
	}

	public UploadOrdersItemsRequest getItems() {
		return items;
	}

	public void setItems(UploadOrdersItemsRequest items) {
		this.items = items;
	}

	public Date getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(Date sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public Date getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Date sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public Double getGoodsValue() {
		return goodsValue;
	}

	public void setGoodsValue(Double goodsValue) {
		this.goodsValue = goodsValue;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Double getItemsValue() {
		return itemsValue;
	}

	public void setItemsValue(Double itemsValue) {
		this.itemsValue = itemsValue;
	}

	public Double getAgencyFund() {
		return agencyFund;
	}

	public void setAgencyFund(Double agencyFund) {
		this.agencyFund = agencyFund;
	}

	public Double getItemsWeight() {
		return itemsWeight;
	}

	public void setItemsWeight(Double itemsWeight) {
		this.itemsWeight = itemsWeight;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getInsuranceValue() {
		return insuranceValue;
	}

	public void setInsuranceValue(Double insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	public Double getTotalServiceFee() {
		return totalServiceFee;
	}

	public void setTotalServiceFee(Double totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}

	public Double getCodSplitFee() {
		return codSplitFee;
	}

	public void setCodSplitFee(Double codSplitFee) {
		this.codSplitFee = codSplitFee;
	}

	public String getBigPen() {
		return bigPen;
	}

	public void setBigPen(String bigPen) {
		this.bigPen = bigPen;
	}
	
}
