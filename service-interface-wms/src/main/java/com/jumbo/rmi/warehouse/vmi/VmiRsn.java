package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VmiRsn收货反馈
 * 
 */
public class VmiRsn implements Serializable {



    private static final long serialVersionUID = -2300424999886157776L;


    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 品牌店铺对接编码
     */
    private String storeCode;

    /**
     * 入库单据编码
     */
    private String orderCode;

    /**
     * 发货地
     */
    private String fromLocation;

    /**
     * 收货地
     */
    private String toLocation;

    /**
     * 到货时间格式：年（4位）月（2位）日（2位）时（2位）分（2位）秒（2位）
     */
    private String receiveDate;

    /**
     * 收货单状态
     */
    private String orderStatus;

    /**
     * 明细行
     */
    private VmiRsnLine[] rsnLines;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     */
    private String extMemo;

    /**
     * 仓库编码
     */
    private String whCode;


    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public VmiRsnLine[] getRsnLines() {
        return rsnLines;
    }

    public void setRsnLines(VmiRsnLine[] rsnLines) {
        this.rsnLines = rsnLines;
    }



}
