package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 入库单反馈明细行
 * 
 */
public class WmsInboundOrderResponseSnLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4544967444250701837L;


    /**
     * 商品对接唯一标识
     */
    private String skuCode;
    /**
     * SN
     */
    private String sn;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

}
