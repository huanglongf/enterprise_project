package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 出库单反馈明细行
 * 
 */
public class WmsOutboundOrderResponseLine implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 5684386878420077906L;
    /**
     * 商品对接唯一标识
     */
    private String skuCode;
    /**
     * 入库数量
     */
    private String qty;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 过期时间格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String expDate;

    /**
     * 箱号
     */
    private String cartonNo;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

}
