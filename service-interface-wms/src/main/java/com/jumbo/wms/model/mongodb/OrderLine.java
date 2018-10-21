package com.jumbo.wms.model.mongodb;

import java.util.Set;

import com.jumbo.wms.model.BaseModel;

public class OrderLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8310829764898351816L;

    private Set<String> skubarcode;

    private Long skuId;

    private Long planQty; // 计划量

    private Long restQty; // 剩余计划量

    private Long falg; // 页面显示用到


    public Long getFalg() {
        return falg;
    }

    public void setFalg(Long falg) {
        this.falg = falg;
    }

    public Set<String> getSkubarcode() {
        return skubarcode;
    }

    public void setSkubarcode(Set<String> skubarcode) {
        this.skubarcode = skubarcode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public Long getRestQty() {
        return restQty;
    }

    public void setRestQty(Long restQty) {
        this.restQty = restQty;
    }

}
