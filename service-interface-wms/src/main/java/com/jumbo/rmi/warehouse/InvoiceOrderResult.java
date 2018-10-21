package com.jumbo.rmi.warehouse;

import java.io.Serializable;

/**
 * 补开发票反馈实体
 * 
 * @author jinlong.ke
 * 
 */
public class InvoiceOrderResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7925390041880224861L;
    /*
     * 订单号
     */
    private String orderCode;
    /*
     * 备注
     */
    private String memo;
    /*
     * 状态
     */
    private int status;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
