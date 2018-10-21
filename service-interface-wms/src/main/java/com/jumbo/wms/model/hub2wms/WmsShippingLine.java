package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class WmsShippingLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4009815734509396091L;
    /*
     * 平台行号 
     */
    private String lineNo;
    /*
     * 商品
     */
    private String sku;
    /*
     * 数量
     */
    private long qty;

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }


}
