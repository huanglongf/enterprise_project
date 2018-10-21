package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VmiAsn 收货信息
 * 
 */
public class VmiRto implements Serializable {


    private static final long serialVersionUID = 3832792228667143511L;

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
     * 到货时间格式：年（4位）月（2位）日（2位）时（2位）分（2位）秒（2位）
     */
    private String arriveDate;

    /**
     * 明细行
     */
    private VmiRtoLine[] rtoLines;

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

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
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

    public VmiRtoLine[] getRtoLines() {
        return rtoLines;
    }

    public void setRtoLines(VmiRtoLine[] rtoLines) {
        this.rtoLines = rtoLines;
    }

}
