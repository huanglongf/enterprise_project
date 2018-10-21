package com.jumbo.wms.model.hub2wms;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class OrderAddInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 887946740581661302L;
    private String orderSource;
    private String orderCode;
    private String owner;
    private Boolean IsAllowDeliver;
    private Boolean IsChangeRecieverInfo;
    private String paymentStatus;
    private Date paymentTime;
    private OrderRecieverInfo recieverInfo;
    private String memo;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getIsAllowDeliver() {
        return IsAllowDeliver;
    }

    public void setIsAllowDeliver(Boolean isAllowDeliver) {
        IsAllowDeliver = isAllowDeliver;
    }

    public Boolean getIsChangeRecieverInfo() {
        return IsChangeRecieverInfo;
    }

    public void setIsChangeRecieverInfo(Boolean isChangeRecieverInfo) {
        IsChangeRecieverInfo = isChangeRecieverInfo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public OrderRecieverInfo getRecieverInfo() {
        return recieverInfo;
    }

    public void setRecieverInfo(OrderRecieverInfo recieverInfo) {
        this.recieverInfo = recieverInfo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
