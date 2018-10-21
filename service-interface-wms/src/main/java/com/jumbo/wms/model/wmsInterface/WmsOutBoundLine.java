package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * WMS通用出库明细
 *
 */
public class WmsOutBoundLine implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7738926142421086279L;
    /**
     * 平台行号
     */
    private String lineNo;
    /**
     * 商品唯一编码
     */
    private String upc;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 店铺CODE
     */
    private String storeCode;
    /**
     * 吊牌价
     */
    private BigDecimal listPrice;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 行折扣
     */
    private BigDecimal lineDiscount;
    /**
     * 行总金额
     */
    private BigDecimal orderTotalActual;
    /**
     * 活动编码
     */
    private String activeCode;
    /**
     * 订单货主
     */
    private String owner;
    /**
     * 商品状态 
     */
    private String invStatus;
    /**
     * 订单行仓库
     */
    private String whCode;

    private String extMemo;

    /**
     * 增值服务
     */
    private List<WmsOutBoundVasLine> wobvlList;

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
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

    public BigDecimal getLineDiscount() {
        return lineDiscount;
    }

    public void setLineDiscount(BigDecimal lineDiscount) {
        this.lineDiscount = lineDiscount;
    }

    public BigDecimal getOrderTotalActual() {
        return orderTotalActual;
    }

    public void setOrderTotalActual(BigDecimal orderTotalActual) {
        this.orderTotalActual = orderTotalActual;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public List<WmsOutBoundVasLine> getWobvlList() {
        return wobvlList;
    }

    public void setWobvlList(List<WmsOutBoundVasLine> wobvlList) {
        this.wobvlList = wobvlList;
    }


}
