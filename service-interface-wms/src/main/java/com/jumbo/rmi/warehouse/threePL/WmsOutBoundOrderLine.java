package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓出库单据明细
 * 
 */
public class WmsOutBoundOrderLine implements Serializable {

    private static final long serialVersionUID = -1417273110458998189L;

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

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 效期范围开始(年4位-月2位-日2位)
     */
    private String expDateStart;

    /**
     * 效期范围结束(年4位-月2位-日2位)
     */
    private String expDateEnd;

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

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getExpDateStart() {
        return expDateStart;
    }

    public void setExpDateStart(String expDateStart) {
        this.expDateStart = expDateStart;
    }

    public String getExpDateEnd() {
        return expDateEnd;
    }

    public void setExpDateEnd(String expDateEnd) {
        this.expDateEnd = expDateEnd;
    }

}
