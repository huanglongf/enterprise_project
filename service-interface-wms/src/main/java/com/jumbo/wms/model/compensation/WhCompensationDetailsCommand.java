package com.jumbo.wms.model.compensation;


/**
 * 索赔单明细
 * 
 * @author lihui
 */
public class WhCompensationDetailsCommand extends WhCompensationDetails {


    private static final long serialVersionUID = -4656554907695004194L;

    /**
     * 平台订单号
     */
    private String erpOrderCode;

    /**
     * 相关单据号
     */
    private String omsOrderCode;

    /**
     * 换货申请码
     */
    private String rasCode;

    private Integer claimAffirmStatus;

    public Integer getClaimAffirmStatus() {
        return claimAffirmStatus;
    }

    public void setClaimAffirmStatus(Integer claimAffirmStatus) {
        this.claimAffirmStatus = claimAffirmStatus;
    }

    public String getErpOrderCode() {
        return erpOrderCode;
    }

    public void setErpOrderCode(String erpOrderCode) {
        this.erpOrderCode = erpOrderCode;
    }

    public String getRasCode() {
        return rasCode;
    }

    public void setRasCode(String rasCode) {
        this.rasCode = rasCode;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }



}
