package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

public class StaAdditionalLineCommand extends StaAdditionalLine {


    /**
	 * 
	 */
    private static final long serialVersionUID = 6475574937931805653L;

    private Long skuId;

    private String skuCode;

    private String trackingValue;

    private Long staId;


    private BigDecimal weight;

    private BigDecimal addLinewidth;

    private BigDecimal height;

    private BigDecimal length;


    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getAddLinewidth() {
        return addLinewidth;
    }

    public void setAddLinewidth(BigDecimal addLinewidth) {
        this.addLinewidth = addLinewidth;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTrackingValue() {
        return trackingValue;
    }

    public void setTrackingValue(String trackingValue) {
        this.trackingValue = trackingValue;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
