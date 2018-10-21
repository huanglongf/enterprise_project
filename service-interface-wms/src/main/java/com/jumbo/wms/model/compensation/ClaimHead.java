package com.jumbo.wms.model.compensation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 索赔单
 * 
 * @author lihui
 */
public class ClaimHead  implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 5451031774023577234L;


    /**
     * 索赔编码
     */
    private String claimCode;
    /**
     * 系统对接编码
     */
    private String systemCode;
    /**
     * 相关单据号(OMS订单号)
     */
    private String omsOrderCode;
    /**
     * 平台订单号
     */
    private String erpOrderCode;

    /**
     * 换货申请编码
     */
    private String rasCode;

    /**
     * 店铺所属
     */
    private String shopOwner;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 物流单号
     * 
     */
    private String transNumber;

    /**
     * 物流商编码
     */
    private String transCode;


    /**
     * 索赔原因
     */
    private Integer claimReason;

    /**
     * 是否外箱破损
     */
    private Boolean isOuterContainerDamaged;

    /**
     * 产品包装是否破损
     */
    private Boolean isPackageDamaged;

    /**
     * 是否二次封箱
     */
    private Boolean isTwoSubBox;

    /**
     * 是否有产品退回
     */
    private Boolean isHasProductReturn;

    /**
     * 箱内填充是否充分
     */
    private Boolean isFilledWith;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附件名称
     */
    private String fileUrl;

    /**
     * 附加金额
     */
    private BigDecimal extralAmt;

    /**
     * 附加金额备注
     */
    private String extralRemark;
   
    /**
     * 申请理赔总金额
     */
    private BigDecimal totalClaimAmt;
   
    /**
     * 明细行
     */
    private List<ClaimLine> lines;


    public String getClaimCode() {
        return claimCode;
    }

    public void setClaimCode(String claimCode) {
        this.claimCode = claimCode;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
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

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Boolean getIsOuterContainerDamaged() {
        return isOuterContainerDamaged;
    }

    public void setIsOuterContainerDamaged(Boolean isOuterContainerDamaged) {
        this.isOuterContainerDamaged = isOuterContainerDamaged;
    }

    public Boolean getIsPackageDamaged() {
        return isPackageDamaged;
    }

    public void setIsPackageDamaged(Boolean isPackageDamaged) {
        this.isPackageDamaged = isPackageDamaged;
    }

    public Boolean getIsTwoSubBox() {
        return isTwoSubBox;
    }

    public void setIsTwoSubBox(Boolean isTwoSubBox) {
        this.isTwoSubBox = isTwoSubBox;
    }

    public Boolean getIsHasProductReturn() {
        return isHasProductReturn;
    }

    public void setIsHasProductReturn(Boolean isHasProductReturn) {
        this.isHasProductReturn = isHasProductReturn;
    }

    public Boolean getIsFilledWith() {
        return isFilledWith;
    }

    public void setIsFilledWith(Boolean isFilledWith) {
        this.isFilledWith = isFilledWith;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ClaimLine> getLines() {
        return lines;
    }

    public void setLines(List<ClaimLine> lines) {
        this.lines = lines;
    }

    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public Integer getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(Integer claimReason) {
        this.claimReason = claimReason;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public BigDecimal getExtralAmt() {
        return extralAmt;
    }

    public void setExtralAmt(BigDecimal extralAmt) {
        this.extralAmt = extralAmt;
    }

    public String getExtralRemark() {
        return extralRemark;
    }

    public void setExtralRemark(String extralRemark) {
        this.extralRemark = extralRemark;
    }

    public BigDecimal getTotalClaimAmt() {
        return totalClaimAmt;
    }

    public void setTotalClaimAmt(BigDecimal totalClaimAmt) {
        this.totalClaimAmt = totalClaimAmt;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

	
}
