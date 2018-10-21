package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓库存调整反馈明细
 * 
 */
public class WmsInvStatusChangeOrderLineResponse implements Serializable {

    private static final long serialVersionUID = -7971774826693762348L;

    /**
     * 商品对接唯一标识
     */
    private String skuCode;
    /**
     * 入库数量
     */
    private Long qty;
    /**
     * 原库存状态
     */
    private String fromInvStatus;
    /**
     * 新库存状态
     */
    private String toInvStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 过期时间
     */
    private String expDate;

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

    public String getFromInvStatus() {
        return fromInvStatus;
    }

    public void setFromInvStatus(String fromInvStatus) {
        this.fromInvStatus = fromInvStatus;
    }

    public String getToInvStatus() {
        return toInvStatus;
    }

    public void setToInvStatus(String toInvStatus) {
        this.toInvStatus = toInvStatus;
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

}
