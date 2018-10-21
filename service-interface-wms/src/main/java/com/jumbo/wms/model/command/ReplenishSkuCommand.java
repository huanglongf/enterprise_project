package com.jumbo.wms.model.command;

import com.jumbo.wms.model.BaseModel;
/**
 * 
 * @author jinlong.ke
 *
 */
public class ReplenishSkuCommand extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 8351885535819857196L;
    private Long skuId;
    private Long qty;
    private String innerShopCode;
    public Long getSkuId() {
        return skuId;
    }
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    public Long getQty() {
        return qty;
    }
    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getInnerShopCode() {
        return innerShopCode;
    }
    public void setInnerShopCode(String innerShopCode) {
        this.innerShopCode = innerShopCode;
    }
}
