package com.jumbo.wms.model.command.expressRadar;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gianni.zhang
 * 
 * 快递/订单信息查询界面
 *
 * 2015年6月1日 下午4:43:58
 */
public class ExpressOrderQueryCommand implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5735041058945777409L;
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 物流服务商id
     */
    private Long lpId;
    
    /**
     * 物流服务商
     */
    private String lpCode;
    
    /**
     * 快递单号
     */
    private String expressCode;
    
    /**
     * 平台订单号
     */
    private String pcode;
    
    /**
     * 发件仓库id
     */
    private Long pwhId;
    
    /**
     * 发件仓库
     */
    private String pwhName;
    
    /**
     * 店铺
     */
    private String owner;
    
    /**
     * 省
     */
    private String province;
    
    /**
     * 市
     */
    private String city;
    
    /**
     * 快递状态
     */
    private String status;
    
    /**
     * 预警类型
     */
    private String warningType;
    
    /**
     * 预警级别
     */
    private String warningLv;
    
    /**
     * 作业单号
     */
    private String orderCode;
    
    /**
     * 揽收时间
     */
    private Date takingTime;
    
    /**
     * 标准时效
     */
    private Long standardDate;
    
    /**
     * 实际时效
     */
    private Long actualDate;
    
    /**
     * 开始时间
     */
    private Date startDate;
    
    /**
     * 结束时间
     */
    private Date endDate;
    
    /**
     * 时效类型
     */
    private Integer timeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLpId() {
        return lpId;
    }

    public void setLpId(Long lpId) {
        this.lpId = lpId;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Long getPwhId() {
        return pwhId;
    }

    public void setPwhId(Long pwhId) {
        this.pwhId = pwhId;
    }

    public String getPwhName() {
        return pwhName;
    }

    public void setPwhName(String pwhName) {
        this.pwhName = pwhName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getWarningLv() {
        return warningLv;
    }

    public void setWarningLv(String warningLv) {
        this.warningLv = warningLv;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(Date takingTime) {
        this.takingTime = takingTime;
    }

    public Long getStandardDate() {
        return standardDate;
    }

    public void setStandardDate(Long standardDate) {
        this.standardDate = standardDate;
    }

    public Long getActualDate() {
        return actualDate;
    }

    public void setActualDate(Long actualDate) {
        this.actualDate = actualDate;
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

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

}
