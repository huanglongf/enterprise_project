package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * EBS订单接口详细信息
 * 
 * @author jinlong.ke
 * 
 */
public class OrderLine implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -227721634166237787L;
    /**
     * Sku唯一编码
     */
    private String skuCode;
    /**
     * sku商品名称
     */
    private String skuName;
    /**
     * 出库数量
     */
    private Long qty;
    /**
     * 成本
     */
    private BigDecimal skuCost;
    /**
     * 总金额
     */
    private BigDecimal totalActual;
    /**
     * 吊牌价
     */
    private BigDecimal listPrice;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 库存状态ID
     */
    private Long invStatusId;
    /**
     * 方向 1入库 2出库
     */
    private Integer direction;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 事务时间
     */
    private Date transactionTime;
    /**
     * 礼品列表
     */
    private List<OrderLineGift> gifts;
    /**
     * 行类型
     */
    private Integer lineType;
    /**
     * 活动
     */
    private String activitySource;
    /**
     * 行折前订单总金额
     */
    private BigDecimal orderTotalBfDiscount;
    /**
     * 总金额（行金额取除行折扣）
     */
    private BigDecimal otderTotalActual;

    /**
     * 生产日期
     */
    private Date productionDate;

    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 指定包装耗材barcode（暂时未用）
     */
    private String materialCode;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getOtderTotalActual() {
        return otderTotalActual;
    }

    public void setOtderTotalActual(BigDecimal otderTotalActual) {
        this.otderTotalActual = otderTotalActual;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public List<OrderLineGift> getGifts() {
        return gifts;
    }

    public void setGifts(List<OrderLineGift> gifts) {
        this.gifts = gifts;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public BigDecimal getOrderTotalBfDiscount() {
        return orderTotalBfDiscount;
    }

    public void setOrderTotalBfDiscount(BigDecimal orderTotalBfDiscount) {
        this.orderTotalBfDiscount = orderTotalBfDiscount;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

}
