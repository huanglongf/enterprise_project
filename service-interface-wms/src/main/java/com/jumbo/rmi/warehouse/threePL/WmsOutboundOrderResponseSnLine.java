package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 出库单反馈明细行
 * 
 */
public class WmsOutboundOrderResponseSnLine implements Serializable {

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

}
