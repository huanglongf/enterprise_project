package com.jumbo.wms.model.compensation;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  索赔处理
 *  @author lihui
 */
public class ClaimProcess implements Serializable{

    private static final long serialVersionUID = -3689268759359144320L;


    private String systemCode;
    private String remark;
    private BigDecimal claimAmt;
    

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(BigDecimal claimAmt) {
        this.claimAmt = claimAmt;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }


    
}
