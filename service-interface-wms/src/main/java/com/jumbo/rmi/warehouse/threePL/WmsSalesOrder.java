package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外包仓销售出库单据
 * 
 */
public class WmsSalesOrder implements Serializable {

    private static final long serialVersionUID = -8133188889558216327L;

    /**
     * 数据唯一标识
     */
    private String uuid;
    /**
     * 仓库编码（WMS分配给第三方仓储编码）
     */
    private String warehouseCode;
    /**
     * 入库单唯一对接编码
     */
    private String orderCode;
    /**
     * 相关单据（提供宝尊系统前端业务单据号）
     */
    private String slipCode;
    /**
     * 相关单据1(相关原始单据编码)
     */
    private String slipCode1;
    /**
     * 相关单据2(平台单据编码)
     */
    private String slipCode2;
    /**
     * 宝尊WMS单据创建日期 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String orderDate;
    /**
     * 订单付款时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String paymentDate;

    /**
     * 单据类型 21 销售出 42 换货出
     */
    private String type;
    /**
     * 货物所属
     */
    private String owner;

    /**
     * 是否快速销售订单
     */
    private Boolean isQs;

    /**
     * 特殊处理订单类型 1：需要特殊处理
     */
    private Integer specialType;

    /**
     * 是否需要发票
     */
    private Boolean isInvoice;

    /**
     * 活动信息（保留）
     */
    private String activitySource;

    /**
     * OTO门店编码
     */
    private String toLocation;

    /**
     * 订单金额（含运费）
     */
    private BigDecimal orderAmount;

    /**
     * 货到付款金额
     * */
    private BigDecimal codAmount;

    private String order_user_mail;

    private String order_user_code;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 出库单明细
     */
    private WmsSalesOrderLine[] lines;

    /**
     * 发票信息
     */
    private WmsSalesOrderInvoice[] invoices;

    /**
     * 整单特殊定制
     */
    private WmsSalesOrderSe[] specialExecutes;

    /**
     * 订单发货信息
     */
    private WmsSalesOrderDelivery deliveryInfo;
    
    /**
     * nike 自提点编码
     */
    private String nikePickUpCode;

    /**
     * nike 自提点类型
     */
    private String nikePickUpType;

    /**
     * 是否是自提
     */
    private Boolean nikeIsPick = false;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getIsQs() {
        return isQs;
    }

    public void setIsQs(Boolean isQs) {
        this.isQs = isQs;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrder_user_mail() {
        return order_user_mail;
    }

    public void setOrder_user_mail(String order_user_mail) {
        this.order_user_mail = order_user_mail;
    }

    public String getOrder_user_code() {
        return order_user_code;
    }

    public void setOrder_user_code(String order_user_code) {
        this.order_user_code = order_user_code;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public WmsSalesOrderLine[] getLines() {
        return lines;
    }

    public void setLines(WmsSalesOrderLine[] lines) {
        this.lines = lines;
    }

    public WmsSalesOrderInvoice[] getInvoices() {
        return invoices;
    }

    public void setInvoices(WmsSalesOrderInvoice[] invoices) {
        this.invoices = invoices;
    }

    public WmsSalesOrderSe[] getSpecialExecutes() {
        return specialExecutes;
    }

    public void setSpecialExecutes(WmsSalesOrderSe[] specialExecutes) {
        this.specialExecutes = specialExecutes;
    }

    public WmsSalesOrderDelivery getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(WmsSalesOrderDelivery deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

	public String getNikePickUpCode() {
		return nikePickUpCode;
	}

	public void setNikePickUpCode(String nikePickUpCode) {
		this.nikePickUpCode = nikePickUpCode;
	}

	public String getNikePickUpType() {
		return nikePickUpType;
	}

	public void setNikePickUpType(String nikePickUpType) {
		this.nikePickUpType = nikePickUpType;
	}

	public Boolean getNikeIsPick() {
		return nikeIsPick;
	}

	public void setNikeIsPick(Boolean nikeIsPick) {
		this.nikeIsPick = nikeIsPick;
	}
    

}
