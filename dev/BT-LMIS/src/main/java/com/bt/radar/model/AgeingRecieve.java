package com.bt.radar.model;

import java.util.Date;

public class AgeingRecieve {
    private String waybill;

    private String ageingId;

    private Date receiveCalTime;

    private Date createTime;

    private Date weightTime;

    private Date platfromPayTime;

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill == null ? null : waybill.trim();
    }

    public String getAgeingId() {
        return ageingId;
    }

    public void setAgeingId(String ageingId) {
        this.ageingId = ageingId == null ? null : ageingId.trim();
    }

    public Date getReceiveCalTime() {
        return receiveCalTime;
    }

    public void setReceiveCalTime(Date receiveCalTime) {
        this.receiveCalTime = receiveCalTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getWeightTime() {
        return weightTime;
    }

    public void setWeightTime(Date weightTime) {
        this.weightTime = weightTime;
    }

    public Date getPlatfromPayTime() {
        return platfromPayTime;
    }

    public void setPlatfromPayTime(Date platfromPayTime) {
        this.platfromPayTime = platfromPayTime;
    }
}