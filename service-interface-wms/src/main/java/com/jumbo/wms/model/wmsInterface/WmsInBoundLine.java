package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;

/**
 * WMS通用收货明细表
 * 
 */
public class WmsInBoundLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7100358996552116705L;

    /**
     * 商品唯一编码
     */
    private String upc;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 入库单箱号
     */
    private String cartonNo;

    /**
     * 店铺CODE
     */
    private String storeCode;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 上位系统行号
     */
    private String extLineNo;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     */
    private String extMemo;


    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtLineNo() {
        return extLineNo;
    }

    public void setExtLineNo(String extLineNo) {
        this.extLineNo = extLineNo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }



}
