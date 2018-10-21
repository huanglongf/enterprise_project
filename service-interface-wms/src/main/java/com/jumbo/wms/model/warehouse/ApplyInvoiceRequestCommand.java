package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class ApplyInvoiceRequestCommand implements Serializable {


    private static final long serialVersionUID = -7646781086899402668L;
    /** 订单号. */
    private String orderCode;
    /** 订单金额，单位分. */
    private String amount;
    /** 订单时间,秒. */
    private String time;
    /** 店铺编码. */
    private String shopCode;
    /** 原始订单中是否勾选了发票. */
    private String isCheckedInvoice;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getIsCheckedInvoice() {
        return isCheckedInvoice;
    }

    public void setIsCheckedInvoice(String isCheckedInvoice) {
        this.isCheckedInvoice = isCheckedInvoice;
    }

}
