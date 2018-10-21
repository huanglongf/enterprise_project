package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓库存数量调整明细
 * 
 */
public class WmsInvChangeOrderLineResponse implements Serializable {

    private static final long serialVersionUID = -7971774826693762348L;

    /**
     * 商品对接唯一标识
     */
    private String skuCode;
    /**
     * 出库数量(出库为负，入库为正)
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

}
