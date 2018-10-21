package com.jumbo.rmi.warehouse;

import java.io.Serializable;

/**
 * OMS外包仓销售出库信息
 * 
 * @author jumbo
 * 
 */
public class OmsOutInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8085698799511301072L;
    private String staCode; // staCode
    private String slipCode; // 相关单据号
    private String logistic;// 快递简称
    private String trackingCode;// 运单号
    private String weight;// 重量

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getLogistic() {
        return logistic;
    }

    public void setLogistic(String logistic) {
        this.logistic = logistic;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
