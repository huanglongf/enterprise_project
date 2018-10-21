package com.jumbo.wms.model.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AutoOutBoundEntity implements Serializable {


    private static final long serialVersionUID = 4077627649884078802L;

    private String systemKey;

    private Long userId;

    private Long staId;

    private String trackingNo;

    private Long ouId;

    private BigDecimal weight;
    
    private Long skuId;
    
    private Long qty;

   // private List<StaAdditionalLine> saddlines;



    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    

}
