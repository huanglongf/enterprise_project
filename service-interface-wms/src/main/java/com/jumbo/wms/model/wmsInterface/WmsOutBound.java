package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * WMS通用出库头信息
 *
 */
public class WmsOutBound implements Serializable {

    private static final long serialVersionUID = 132182531268576347L;

    /**
     * 上位系统订单号
     */
    private String extCode;
    /**
     * 电商平台订单号
     */
    private String ecOrderCode;
    /**
     * 店铺OWNER
     */
    private String owner;
    /**
     * 前置单据类型
     */
    private Integer refSlipType;
    /**
     * WMS单据类型
     */
    private Integer wmsType;
    /**
     * 上位系统单据类型
     */
    private String extType;
    /**
     * 仓库编码
     */
    private String whCode;
    /**
     * 是否锁定
     */
    private Boolean isLocked;
    /**
     * 预计发货时间
     */
    private Date planOutboundTime;
    /**
     * 预计送达时间
     */
    private Date planArriveTime;
    /**
     * 是否快递升级
     */
    private Boolean isTransUpgrade;
    /**
     * 发货备注
     */
    private String memo;
    /**
     * cod付款金额 
     */
    private BigDecimal codAmt;
    /**
     * 订单成金额 
     */
    private BigDecimal orderAmt;
    /**
     * 订单优惠金额 
     */
    private BigDecimal orderDiscount;
    /***
     * 订单运费
     */
    private BigDecimal orderTransferFree;
    /**
     * 平台订单创建时间
     */
    private Date orderCreateTime;
    /**
     * 门店编码 
     */
    private String storeCode;
    /***
     * 活动编码
     */
    private String activitySource;

    /***
     * nike 自提点编码
     */
    private String nikePickUpCode;

    /***
     * nike 自提点类型
     */
    private String nikePickUpType;

    /***
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;
    /***
     * 数据来源 区分上位系统
     */
    private String dataSource;

    /**
     * 出库单明细行
     */
    private List<WmsOutBoundLine> wmsOutBoundLines;

    /**
     * 出库物流相关信息
     */
    private WmsOutBoundDeliveryInfo wmsOutBoundDeliveryInfo;

    /**
     * 出库发票信息
     */
    private List<WmsOutBoundInvoice> wmsOutBoundInvoices;

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getEcOrderCode() {
        return ecOrderCode;
    }

    public void setEcOrderCode(String ecOrderCode) {
        this.ecOrderCode = ecOrderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getRefSlipType() {
        return refSlipType;
    }

    public void setRefSlipType(Integer refSlipType) {
        this.refSlipType = refSlipType;
    }

    public Integer getWmsType() {
        return wmsType;
    }

    public void setWmsType(Integer wmsType) {
        this.wmsType = wmsType;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Date getPlanOutboundTime() {
        return planOutboundTime;
    }

    public void setPlanOutboundTime(Date planOutboundTime) {
        this.planOutboundTime = planOutboundTime;
    }

    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public Boolean getIsTransUpgrade() {
        return isTransUpgrade;
    }

    public void setIsTransUpgrade(Boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(BigDecimal codAmt) {
        this.codAmt = codAmt;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(BigDecimal orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public BigDecimal getOrderTransferFree() {
        return orderTransferFree;
    }

    public void setOrderTransferFree(BigDecimal orderTransferFree) {
        this.orderTransferFree = orderTransferFree;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
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

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<WmsOutBoundLine> getWmsOutBoundLines() {
        return wmsOutBoundLines;
    }

    public void setWmsOutBoundLines(List<WmsOutBoundLine> wmsOutBoundLines) {
        this.wmsOutBoundLines = wmsOutBoundLines;
    }

    public WmsOutBoundDeliveryInfo getWmsOutBoundDeliveryInfo() {
        return wmsOutBoundDeliveryInfo;
    }

    public void setWmsOutBoundDeliveryInfo(WmsOutBoundDeliveryInfo wmsOutBoundDeliveryInfo) {
        this.wmsOutBoundDeliveryInfo = wmsOutBoundDeliveryInfo;
    }

    public List<WmsOutBoundInvoice> getWmsOutBoundInvoices() {
        return wmsOutBoundInvoices;
    }

    public void setWmsOutBoundInvoices(List<WmsOutBoundInvoice> wmsOutBoundInvoices) {
        this.wmsOutBoundInvoices = wmsOutBoundInvoices;
    }


}
