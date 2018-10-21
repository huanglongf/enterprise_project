package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;
import java.util.Date;

/**
 * VMI 出库反馈
 * 
 */
public class VmiOutBound implements Serializable {

    private static final long serialVersionUID = -4757212509788451022L;
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
    private VmiOutBoundLine[] vmiOutBoundLine;


    /**
     * 出库时间
     */
    private Date outBoundTime;

    /**
     * 批次号
     */
    private String batchCode;
    private String extMemo;
    /**
     * 仓库编码
     */
    private String whCode;
    /**
     * 库存状态
     */
    private String invStatus;

    public String getWhCode() {
        return whCode;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
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

    public Date getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(Date outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public VmiOutBoundLine[] getVmiOutBoundLine() {
        return vmiOutBoundLine;
    }

    public void setVmiOutBoundLine(VmiOutBoundLine[] vmiOutBoundLine) {
        this.vmiOutBoundLine = vmiOutBoundLine;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }



}
