package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VmiAdj调整
 * 
 */
public class VmiAdj implements Serializable {

    private static final long serialVersionUID = -666856567133084146L;

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
    private String adjDate;

    /**
     * 退仓明细
     */
    private VmiAdjLine[] adjLines;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     */
    private String extMemo;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 调整原因
     */
    private String adjReason;

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

    public String getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(String adjDate) {
        this.adjDate = adjDate;
    }

    public String getAdjReason() {
        return adjReason;
    }

    public void setAdjReason(String adjReason) {
        this.adjReason = adjReason;
    }

    public VmiAdjLine[] getAdjLines() {
        return adjLines;
    }

    public void setAdjLines(VmiAdjLine[] adjLines) {
        this.adjLines = adjLines;
    }

}
