package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VmiRtw退仓
 * 
 */
public class VmiRtw implements Serializable {


    private static final long serialVersionUID = -5156990059882260726L;

    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 品牌店铺对接编码
     */
    private String storeCode;

    /**
     * 退仓单据编码
     */
    private String orderCode;

    /**
     * 退仓时间格式：年（4位）月（2位）日（2位）时（2位）分（2位）秒（2位）
     */
    private String returnDate;

    /**
     * 退仓明细
     */
    private VmiRtwLine[] rtwLines;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     */
    private String extMemo;

    /**
     * 仓库编码
     */
    private String whCode;

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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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

    public VmiRtwLine[] getRtwLines() {
        return rtwLines;
    }

    public void setRtwLines(VmiRtwLine[] rtwLines) {
        this.rtwLines = rtwLines;
    }



}
