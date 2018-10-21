package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;

public class WmsSalesOrderPayment extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4161526610607318940L;
    /*
     * 金额
     */
    private BigDecimal amt;
    /*
     * 类型 
     */
    private int type;
    /*
     * 支付说明
     */
    private String memo;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
