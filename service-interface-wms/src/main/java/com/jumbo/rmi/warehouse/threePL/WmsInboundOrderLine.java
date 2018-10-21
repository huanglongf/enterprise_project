package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓入库单据明细
 * 
 */
public class WmsInboundOrderLine implements Serializable {

    private static final long serialVersionUID = 2662871087061518565L;

    /**
     * 货物所属
     */
    private String owner;
    /**
     * 商品对接唯一编码
     */
    private String skuCode;
    /**
     * 商品数量
     */
    private Long qty;
    /**
     * 库存状态
     */
    private String invStatus;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

}
