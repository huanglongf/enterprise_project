package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class WmsOrderLine extends BaseModel {

    private static final long serialVersionUID = 2889471743067337352L;

    private String lineNo;// 明细编号

    private String sku;// sku编码

    private Integer qty;// 出货数量

    private String orderLineNo;



    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
