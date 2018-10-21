package com.bt.workOrder.model;

import java.math.BigDecimal;
import java.util.Date;

public class OMSClaim {
	private Integer id;
	private String bat_id;
	private String systemCode;
	private String claimCode;
	private String erpOrderCode;
	private String omsOrderCode;
	private String rasCode;
	private String shopOwner;
	private String createTime;
	private String createUserName;
	private String transName;
	private String transCode;
	private String transNumber;
	private String claimReason;
	private String isOuterContainerDamaged;
	private String isPackageDamaged;
	private String isTwoSubBox;
	private String isHasProductReturn;
	private String isFilledWith;
	private String remark;
	private String fileUrl;
	private BigDecimal extralAmt;
	private String extralRemark;
	private BigDecimal totalClaimAmt;
	private Date createDate;
	private Integer is_complete;
	private Boolean claim_status;
	private Integer pro_flag;
	private String pro_remark;
	private Boolean exception_handling;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}
	public String getErpOrderCode() {
		return erpOrderCode;
	}
	public void setErpOrderCode(String erpOrderCode) {
		this.erpOrderCode = erpOrderCode;
	}
	public String getOmsOrderCode() {
		return omsOrderCode;
	}
	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getTransNumber() {
		return transNumber;
	}
	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}
	public String getClaimReason() {
		return claimReason;
	}
	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}
	public String getIsOuterContainerDamaged() {
		return isOuterContainerDamaged;
	}
	public void setIsOuterContainerDamaged(String isOuterContainerDamaged) {
		this.isOuterContainerDamaged = isOuterContainerDamaged;
	}
	public String getIsPackageDamaged() {
		return isPackageDamaged;
	}
	public void setIsPackageDamaged(String isPackageDamaged) {
		this.isPackageDamaged = isPackageDamaged;
	}
	public String getIsTwoSubBox() {
		return isTwoSubBox;
	}
	public void setIsTwoSubBox(String isTwoSubBox) {
		this.isTwoSubBox = isTwoSubBox;
	}
	public String getIsHasProductReturn() {
		return isHasProductReturn;
	}
	public void setIsHasProductReturn(String isHasProductReturn) {
		this.isHasProductReturn = isHasProductReturn;
	}
	public String getIsFilledWith() {
		return isFilledWith;
	}
	public void setIsFilledWith(String isFilledWith) {
		this.isFilledWith = isFilledWith;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getIs_complete() {
		return is_complete;
	}
	public void setIs_complete(Integer is_complete) {
		this.is_complete = is_complete;
	}
	public Boolean getClaim_status() {
		return claim_status;
	}
	public void setClaim_status(Boolean claim_status) {
		this.claim_status = claim_status;
	}
	public Integer getPro_flag() {
		return pro_flag;
	}
	public void setPro_flag(Integer pro_flag) {
		this.pro_flag = pro_flag;
	}
	public String getPro_remark() {
		return pro_remark;
	}
	public void setPro_remark(String pro_remark) {
		this.pro_remark = pro_remark;
	}
	public Boolean getException_handling() {
		return exception_handling;
	}
	public void setException_handling(Boolean exception_handling) {
		this.exception_handling = exception_handling;
	}
	
}
