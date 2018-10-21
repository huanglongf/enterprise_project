package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

public class MsgRtnOutboundCommand extends MsgRtnOutbound {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7239983698003829175L;

    public Long ouId;
    public Long staId;
    public Long creatorId;

    private Long rtnId;

    private String rtnStaCode;

    private Date rtnCreateTime;

    private String shopName;

    private String ouName;

    public Long getRtnId() {
        return rtnId;
    }

    public void setRtnId(Long rtnId) {
        this.rtnId = rtnId;
    }



    public String getRtnStaCode() {
        return rtnStaCode;
    }

    public void setRtnStaCode(String rtnStaCode) {
        this.rtnStaCode = rtnStaCode;
    }

    public Date getRtnCreateTime() {
        return rtnCreateTime;
    }

    public void setRtnCreateTime(Date rtnCreateTime) {
        this.rtnCreateTime = rtnCreateTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

}
