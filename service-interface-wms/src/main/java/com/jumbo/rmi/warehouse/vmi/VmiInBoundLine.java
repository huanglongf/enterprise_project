package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * VMI 退换货入反馈明细
 * 
 */
public class VmiInBoundLine implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 3016790054066497437L;

    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 数量
     */
    private Long qty;

    private String upc;

    private String extMemo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }



}
