package com.jumbo.wms.model;

import java.util.Date;

import javax.persistence.Column;

public class MongoDBOrderAddInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -5527207882545096866L;
    /**
     * 订单来源(OMS/PAC)
     */
    private String orderSource;
    /**
     * 订单号(数据唯一标识)
     */
    private String orderCode;
    /**
     * 店铺编码
     */
    private String owner;
    /**
     * 是否允许发货标识
     */
    private Boolean IsAllowDeliver;
    /**
     * 是否修改收货人相关信息
     */
    private Boolean IsChangeRecieverInfo;
    /**
     * 付款状态(已全额收款/部分收款)
     */
    private String paymentStatus;
    /**
     * 付尾款时间
     */
    private Date paymentTime;
    /**
     * 备注
     */
    private String memo;

    @Column(name = "orderSource")
    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    @Column(name = "orderCode")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "IsAllowDeliver")
    public Boolean getIsAllowDeliver() {
        return IsAllowDeliver;
    }

    public void setIsAllowDeliver(Boolean isAllowDeliver) {
        IsAllowDeliver = isAllowDeliver;
    }

    @Column(name = "IsChangeRecieverInfo")
    public Boolean getIsChangeRecieverInfo() {
        return IsChangeRecieverInfo;
    }

    public void setIsChangeRecieverInfo(Boolean isChangeRecieverInfo) {
        IsChangeRecieverInfo = isChangeRecieverInfo;
    }

    @Column(name = "paymentStatus")
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Column(name = "paymentTime")
    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
