package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外包仓销售出库单据明细
 * 
 */
public class WmsSalesOrderLine implements Serializable {

    private static final long serialVersionUID = -1417273110458998189L;

    /**
     * 货物所属
     */
    private String owner;
    /**
     * 商品对接唯一编码
     */
    private String skuCode;
    /**
     * 商品数量
     */
    private Long qty;
    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 活动(保留)
     */
    private String activitySource;

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

    private WmsSalesOrderGiftLine[] gifts;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 效期范围开始(年4位-月2位-日2位)
     */
    private String expDateStart;

    /**
     * 效期范围结束(年4位-月2位-日2位)
     */
    private String expDateEnd;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
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

    public WmsSalesOrderGiftLine[] getGifts() {
        return gifts;
    }

    public void setGifts(WmsSalesOrderGiftLine[] gifts) {
        this.gifts = gifts;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getExpDateStart() {
        return expDateStart;
    }

    public void setExpDateStart(String expDateStart) {
        this.expDateStart = expDateStart;
    }

    public String getExpDateEnd() {
        return expDateEnd;
    }

    public void setExpDateEnd(String expDateEnd) {
        this.expDateEnd = expDateEnd;
    }


}
