package com.jumbo.mq;

import java.math.BigDecimal;
import java.util.Date;


public class MqRefundMsg {

    /**
     * oms对应退款申请编码
     */
    private String refundCode;

    /**
     * 退款执行时间
     */
    private Date refundTime;

    /**
     * 商城订单编码
     */
    private String bsSoCode;

    /**
     * oms相关退换货编码
     */
    private String raCode;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 退款总金额
     */
    private BigDecimal totalFee;

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getBsSoCode() {
        return bsSoCode;
    }

    public void setBsSoCode(String bsSoCode) {
        this.bsSoCode = bsSoCode;
    }

    public String getRaCode() {
        return raCode;
    }

    public void setRaCode(String raCode) {
        this.raCode = raCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

}
