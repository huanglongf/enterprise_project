package com.jumbo.wms.model.hub2wms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * HUB2WMS过仓 订单发票信息
 * 
 * @author jinlong.ke
 * 
 */
public class WmsOrderInvoice implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2702767999050409063L;
    /*
     * 开票日期
     */
    private String invoiceDate;
    /*
     * 发票抬头
     */
    private String payer;
    /*
     * 发票编码
     */
    private String invoiceCode;
    /*
     * 仓库发票编码
     */
    private String wmsInvoiceCode;
    /*
     * 商品
     */
    private String item;
    /*
     * 发票号
     */
    private String invoiceNo;
    /*
     * 数量
     */
    private Long qty;
    /*
     * 单价
     */
    private BigDecimal unitPrice;
    /*
     * 总金额
     */
    private BigDecimal amt;
    /*
     * 发票备注
     */
    private String memo;
    /*
     * 收款人
     */
    private String payee;
    /*
     * 发票明细
     */
    private List<WmsOrderInvoiceLine> detials;
    /*
     * 公司
     */
    private String company;
    /*
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

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getWmsInvoiceCode() {
        return wmsInvoiceCode;
    }

    public void setWmsInvoiceCode(String wmsInvoiceCode) {
        this.wmsInvoiceCode = wmsInvoiceCode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
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

    public List<WmsOrderInvoiceLine> getDetials() {
        return detials;
    }

    public void setDetials(List<WmsOrderInvoiceLine> detials) {
        this.detials = detials;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

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


}
