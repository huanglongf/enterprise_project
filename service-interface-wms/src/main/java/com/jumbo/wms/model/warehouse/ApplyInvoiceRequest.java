package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class ApplyInvoiceRequest implements Serializable {

    private static final long serialVersionUID = -6109698793252749530L;

    /** 订单号. */
    private String orderCode;
    /** 订单金额，单位分. */
    private Integer amount;
    /** 订单时间,秒. */
    private Long time;
    /** 店铺编码. */
    private String shopCode;
    /** 原始订单中是否勾选了发票. */
    private boolean isCheckedInvoice;

    public ApplyInvoiceRequest() {
        super();
    }

    public ApplyInvoiceRequest(String orderCode, Integer amount, Long time, String shopCode, boolean isCheckedInvoice) {
        super();
        this.orderCode = orderCode;
        this.amount = amount;
        this.time = time;
        this.shopCode = shopCode;
        this.isCheckedInvoice = isCheckedInvoice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public boolean isCheckedInvoice() {
        return isCheckedInvoice;
    }

    public void setCheckedInvoice(boolean isCheckedInvoice) {
        this.isCheckedInvoice = isCheckedInvoice;
    }


}
