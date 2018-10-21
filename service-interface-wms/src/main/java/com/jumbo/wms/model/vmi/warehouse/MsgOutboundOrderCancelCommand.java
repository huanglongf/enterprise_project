package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;



public class MsgOutboundOrderCancelCommand extends MsgOutboundOrderCancel {

    private static final long serialVersionUID = -8108737206561840310L;

    private Date starteDate;
    private Date endDate;

    private String slipCode;

    private int statusId;

    private Long ouId;
    /*
     * 新增String类型字段，辅助完成查询时间精确到时分秒
     */
    private String starteDate1;
    private String endDate1;
    /**
     * 作业单类型
     */
    private String staType;

    private Long staId;

    @Override
    public Long getStaId() {
        return staId;
    }

    @Override
    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getStarteDate() {
        return starteDate;
    }

    public void setStarteDate(Date starteDate) {
        this.starteDate = starteDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStarteDate1() {
        return starteDate1;
    }

    public void setStarteDate1(String starteDate1) {
        this.starteDate1 = starteDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

}
