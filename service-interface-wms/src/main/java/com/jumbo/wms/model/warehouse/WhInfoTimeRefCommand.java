package com.jumbo.wms.model.warehouse;

public class WhInfoTimeRefCommand extends WhInfoTimeRef {

    /**
     * 
     */
    private static final long serialVersionUID = -9097852963258119281L;

    private String operator;

    private String code;

    private String intStatus;

    private String refSlipCode;



    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(String intStatus) {
        this.intStatus = intStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }



}
