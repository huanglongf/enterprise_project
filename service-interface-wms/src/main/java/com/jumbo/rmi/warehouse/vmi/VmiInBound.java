package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;
import java.util.Date;

/**
 * VMI 退换货入反馈
 * 
 */
public class VmiInBound implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3690432950226757132L;

    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 品牌对接码
     */
    private String storeCode;

    /**
     * 客户引用
     */
    private String customerRef;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 反馈明细
     */
    private VmiInBoundLine[] vmiInBoundLine;

    /**
     * 入库时间
     */
    private Date inBoundTime;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 入库状态
     */
    private String invStatus;



    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    private String extMemo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Date getInBoundTime() {
        return inBoundTime;
    }

    public void setInBoundTime(Date inBoundTime) {
        this.inBoundTime = inBoundTime;
    }

    public VmiInBoundLine[] getVmiInBoundLine() {
        return vmiInBoundLine;
    }

    public void setVmiInBoundLine(VmiInBoundLine[] vmiInBoundLine) {
        this.vmiInBoundLine = vmiInBoundLine;
    }



}
