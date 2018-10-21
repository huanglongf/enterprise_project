package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS过仓 单据包装
 * 
 * @author jinlong.ke
 * 
 */
public class WmsOrderPacking extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5175154317913705117L;

    /*
     * 类型 
     */
    private int type;
    /*
     * 商品
     */
    private String sku;
    /*
     * 说明
     */
    private String memo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
