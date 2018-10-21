package com.jumbo.wms.model.pda;

import java.io.Serializable;

public class InboundOnShelvesDetailCommand implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1689437695115654896L;
	private String code;
    private String slipCode;
    private String uniqueCode;
    private String skuCode;
    private String location;
    private String qty;
    private Long detailId;
    private Integer pgIndex;
    private String invStatus;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Integer getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }
}
