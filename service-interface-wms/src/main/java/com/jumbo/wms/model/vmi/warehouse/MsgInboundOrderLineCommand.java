package com.jumbo.wms.model.vmi.warehouse;

import java.math.BigDecimal;

public class MsgInboundOrderLineCommand extends MsgInboundOrderLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3340976732052569741L;
    private String spuCode;
    private String skuName;
    private Long invStatusId;
    private BigDecimal fob;
    private String barCode;
    private String ecode;
    private String ecode2;
    private String invStsName;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getFob() {
        return fob;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getInvStsName() {
        return invStsName;
    }

    public void setInvStsName(String invStsName) {
        this.invStsName = invStsName;
    }

    public String getEcode2() {
        return ecode2;
    }

    public void setEcode2(String ecode2) {
        this.ecode2 = ecode2;
    }

}
