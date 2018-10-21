package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流信息
 * 
 * @author cheng.su
 * 
 */
public class WmsTransInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -6574485023320766152L;
    /**
     * 物流商简称
     */
    private String transCode;
    /**
     * 运单号
     */
    private String transNo;
    /**
     * 物流时效
     */
    private int transTimeType;

    private BigDecimal weight; // 重量

    private BigDecimal volume; // 体积

    private Integer cartonQuantity; // 箱数


    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Integer getCartonQuantity() {
        return cartonQuantity;
    }

    public void setCartonQuantity(Integer cartonQuantity) {
        this.cartonQuantity = cartonQuantity;
    }


    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public int getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(int transTimeType) {
        this.transTimeType = transTimeType;
    }



}
