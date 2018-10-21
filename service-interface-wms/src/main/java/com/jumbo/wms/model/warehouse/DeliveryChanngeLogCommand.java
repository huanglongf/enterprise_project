package com.jumbo.wms.model.warehouse;

import java.util.Date;

/**
 * 
 * @author jinggang.chen
 * 
 */
public class DeliveryChanngeLogCommand extends DeliveryChanngeLog {

    private static final long serialVersionUID = -8517431576085090270L;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 相关单据号
     */
    private String slipCode;

    /**
     * 平台订单号
     */
    private String slipcode1;

    /**
     * 店铺
     */
    private String channel;
    
    /**
     * 查询起始时间
     */
    private Date startDate;
    
    /**
     * 查询结束时间
     */
    private Date endDate;

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

    public String getSlipcode1() {
        return slipcode1;
    }

    public void setSlipcode1(String slipcode1) {
        this.slipcode1 = slipcode1;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
