package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class WmsAdvanceOrderResult extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 7631942088273048305L;
    private String orderSource;
    private String orderCode;
    private String memo;
    private int status;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
