package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Invoice implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5897822337126963611L;
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
     * 开票人
     */
    private String payee;
    /**
     * 开票人
     */
    private String drawer;
    /**
     * 付款单位纳税人识别号
     */
    private String identificationNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String telephone;
    /**
     * 发票明细
     */
    private List<InvoiceLine> detials;



    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public void setDetials(List<InvoiceLine> detials) {
        this.detials = detials;
    }

    public List<InvoiceLine> getDetials() {
        return detials;
    }



}
