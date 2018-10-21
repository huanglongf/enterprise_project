package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出库单整单礼品明细
 * 
 */
public class WmsSalesOrderInvoice implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -414350593591307114L;

    /**
     * 开票日期
     */
    private String invoiceDate;

    /**
     * 付款单位（发票抬头）
     */
    private String payer;

    /**
     * 商品
     */
    private String item;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 总金额
     */
    private BigDecimal amt;

    /**
     * 发票备注
     */
    private String memo;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 发票明细
     */
    private WmsSalesOrderInvoiceLine[] detials;

    /**
     * 开票人
     */
    private String drawer;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 公司简称
     */
    private String companyName;

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public WmsSalesOrderInvoiceLine[] getDetials() {
        return detials;
    }

    public void setDetials(WmsSalesOrderInvoiceLine[] detials) {
        this.detials = detials;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
