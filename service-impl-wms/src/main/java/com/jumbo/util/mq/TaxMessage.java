package com.jumbo.util.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TaxMessage implements Serializable {

    private static final long serialVersionUID = -2567324997013736642L;

    private Date inivoiceDate; // 开票日期

    private String payer; // 付款单位

    private String item; // 经营项目

    private BigDecimal price; // 单价

    private BigDecimal amount; // 开票金额

    private String payee; // 收款人

    private String drawer; // 开票人

    private String remark; // 备注

    private String uniqueIdentify; // 唯一标识

    private String relatedCode; // 相关单据号

    public Date getInivoiceDate() {
        return inivoiceDate;
    }

    public void setInivoiceDate(Date inivoiceDate) {
        this.inivoiceDate = inivoiceDate;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUniqueIdentify() {
        return uniqueIdentify;
    }

    public void setUniqueIdentify(String uniqueIdentify) {
        this.uniqueIdentify = uniqueIdentify;
    }

    public String getRelatedCode() {
        return relatedCode;
    }

    public void setRelatedCode(String relatedCode) {
        this.relatedCode = relatedCode;
    }


}
