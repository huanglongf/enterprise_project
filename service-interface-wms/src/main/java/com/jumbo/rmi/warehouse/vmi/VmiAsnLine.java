package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VmiRsnLine 收货信息明细
 * 
 */
public class VmiAsnLine implements Serializable {

    private static final long serialVersionUID = -5833854488136833909L;

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



}
