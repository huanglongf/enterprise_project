package com.jumbo.webservice.nike.command;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: hlz Date: 2010-8-16 Time: 13:31:57 To change this template use
 * File | Settings | File Templates.
 */
public class NikeOrderObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1583073695445849846L;
    private String code;
    private String orderType;
    private String remark;
    private NikeDeliveryInfo nikeDeliveryInfo;
    private String invoiceTitle;
    private String invoiceContent;
    private String totalAmount;// 销售金额

    /*
     * private String[] upcCodes; private Integer[] qtys;
     */
    private NikeOrderLineObj[] lineObjs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public NikeDeliveryInfo getNikeDeliveryInfo() {
        return nikeDeliveryInfo;
    }

    public void setNikeDeliveryInfo(NikeDeliveryInfo nikeDeliveryInfo) {
        this.nikeDeliveryInfo = nikeDeliveryInfo;
    }

    public NikeOrderLineObj[] getLineObjs() {
        return lineObjs;
    }

    public void setLineObjs(NikeOrderLineObj[] lineObjs) {
        this.lineObjs = lineObjs;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
