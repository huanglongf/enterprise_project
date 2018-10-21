package com.jumbo.wms.model.command;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流面单维护Command
 * 
 * @author jinlong.ke
 * 
 */
public class StaDeliverCommand extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 7492334981432757769L;
    /**
     * 相关单据号
     */
    private String slipCode;
    /**
     * 物流面单号
     */
    private String transNo;

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
}
