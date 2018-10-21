package com.jumbo.wms.model.vmi.espData;


public class ESPOrderCommand extends ESPOrder {
    /**
	 * 
	 */
    private static final long serialVersionUID = 2924306046343210455L;


    private Long skuId;

    private Long stalineId;
    private Long totalQuantity;
    private Long stvId;
    private Long whId;
    private String barCode;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStalineId() {
        return stalineId;
    }

    public void setStalineId(Long stalineId) {
        this.stalineId = stalineId;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


}
