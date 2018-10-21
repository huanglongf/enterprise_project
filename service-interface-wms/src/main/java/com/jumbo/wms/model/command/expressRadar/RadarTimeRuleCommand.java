package com.jumbo.wms.model.command.expressRadar;

import com.jumbo.wms.model.expressRadar.RadarTimeRule;

public class RadarTimeRuleCommand extends RadarTimeRule {

    private static final long serialVersionUID = -4179731449376287358L;

    private Long phyid;

    private String phyName;

    private String lpCode;// 物流商

    private Integer wlService;// 物流服务

    private Integer dateTimeType;// 时效类型

    private String province;// 省

    private String city;// 市

    private String hour;// 截止揽件时间小时

    private String minute;// 截止揽件时间分钟

    private String dateTime;

    private String wlServiceString;// 物流服务

    private String dateTimeTypeString;// 时效类型

    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWlServiceString() {
        return wlServiceString;
    }

    public void setWlServiceString(String wlServiceString) {
        this.wlServiceString = wlServiceString;
    }

    public String getDateTimeTypeString() {
        return dateTimeTypeString;
    }

    public void setDateTimeTypeString(String dateTimeTypeString) {
        this.dateTimeTypeString = dateTimeTypeString;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Long getPhyid() {
        return phyid;
    }

    public void setPhyid(Long phyid) {
        this.phyid = phyid;
    }

    public String getPhyName() {
        return phyName;
    }

    public void setPhyName(String phyName) {
        this.phyName = phyName;
    }

    public Integer getWlService() {
        return wlService;
    }

    public void setWlService(Integer wlService) {
        this.wlService = wlService;
    }

    public Integer getDateTimeType() {
        return dateTimeType;
    }

    public void setDateTimeType(Integer dateTimeType) {
        this.dateTimeType = dateTimeType;
    }


}
