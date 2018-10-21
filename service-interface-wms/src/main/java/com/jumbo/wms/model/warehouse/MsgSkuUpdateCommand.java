package com.jumbo.wms.model.warehouse;

import java.io.Serializable;
import java.util.Date;

public class MsgSkuUpdateCommand implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1533494307389451489L;

	private Long id;

    private Long skuId;

    private Long cId;

    private Integer validDate;

    private Integer timeType;

    private Integer exeCount;

    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer getValidDate() {
        return validDate;
    }

    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getExeCount() {
        return exeCount;
    }

    public void setExeCount(Integer exeCount) {
        this.exeCount = exeCount;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

}
