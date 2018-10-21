package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出库单整单礼品明细
 * 
 */
public class WmsSalesOrderInvoiceLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5050616331294930153L;

    /**
     * 商品
     */
    private String iteam;
    /**
     * 件数
     */
    private Integer qty;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 含总金额
     */
    private BigDecimal amt;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    public String getIteam() {
        return iteam;
    }

    public void setIteam(String iteam) {
        this.iteam = iteam;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

}
