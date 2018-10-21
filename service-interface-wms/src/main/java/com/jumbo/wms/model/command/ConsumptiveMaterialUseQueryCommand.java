package com.jumbo.wms.model.command;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * Data Transfer Object for ConsumptiveMaterial Use Query
 * 
 * @author jinlong.ke
 * 
 */
public class ConsumptiveMaterialUseQueryCommand extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -6736776810293238619L;
    /**
     * sequence
     */
    private Long id;
    /**
     * outbound time
     */
    private Date outboundTime;
    private String slipCode;
    private String recommandSku;
    private String usedSku;
    private String isMatch;
    private String checkUser;
    private String outboundUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getRecommandSku() {
        return recommandSku;
    }

    public void setRecommandSku(String recommandSku) {
        this.recommandSku = recommandSku;
    }

    public String getUsedSku() {
        return usedSku;
    }

    public void setUsedSku(String usedSku) {
        this.usedSku = usedSku;
    }

    public String getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(String isMatch) {
        this.isMatch = isMatch;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getOutboundUser() {
        return outboundUser;
    }

    public void setOutboundUser(String outboundUser) {
        this.outboundUser = outboundUser;
    }
}
