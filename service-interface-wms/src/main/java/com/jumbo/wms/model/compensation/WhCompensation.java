package com.jumbo.wms.model.compensation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * 索赔单
 * 
 * @author lihui
 */
@Entity
@Table(name = "T_WH_COMPENSATION")
public class WhCompensation extends BaseModel {

    private static final long serialVersionUID = -1819953628877469156L;

    private Long id;
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
    private SysCompensateCause sysCompensateCause;

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
     * 附件下载地址
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
     * 状态
     */
    private CompensateStatus status;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 物流部赔偿金额
     */
    private Double logisticsepartmentAmt;

    /**
     * 快递商赔偿金额
     */
    private Double expressDeliveryAmt;

    /**
     * 处理结果备注
     */
    private String disposeRemark;
   
    /**
     * 处理时间
     */
    private Date disposeTime;

    /**
     * 确认时间
     */
    private Date affirmTime;

    private Integer claimAffirmStatus;



    /**
     * 最后修改人
     */
    private User lastUpdateUser;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_COMPENSATION", sequenceName = "S_T_WH_COMPENSATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_COMPENSATION")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.compensation.CompensateStatus")})
    public CompensateStatus getStatus() {
		return status;
	}

    public void setStatus(CompensateStatus status) {
		this.status = status;
	}

    @Column(name = "CLAIM_CODE")
    public String getClaimCode() {
        return claimCode;
    }

    public void setClaimCode(String claimCode) {
        this.claimCode = claimCode;
    }

    @Column(name = "OMS_ORDER_CODE")
    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    @Column(name = "ERP_ORDER_CODE")
    public String getErpOrderCode() {
        return erpOrderCode;
    }

    public void setErpOrderCode(String erpOrderCode) {
        this.erpOrderCode = erpOrderCode;
    }

    @Column(name = "RAS_CODE")
    public String getRasCode() {
        return rasCode;
    }

    public void setRasCode(String rasCode) {
        this.rasCode = rasCode;
    }

    @Column(name = "SHOP_OWNER")
    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    @Column(name = "CREATE_USER_NAME")
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }



    @Column(name = "TRANS_CODE")
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPENSATE_CAUSE_ID")
    public SysCompensateCause getSysCompensateCause() {
        return sysCompensateCause;
    }

    public void setSysCompensateCause(SysCompensateCause sysCompensateCause) {
        this.sysCompensateCause = sysCompensateCause;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    @Column(name = "EXTRAL_AMT")
    public BigDecimal getExtralAmt() {
        return extralAmt;
    }

    public void setExtralAmt(BigDecimal extralAmt) {
        this.extralAmt = extralAmt;
    }

    @Column(name = "TOTAL_CLAIM_AMT")
    public BigDecimal getTotalClaimAmt() {
        return totalClaimAmt;
    }

    public void setTotalClaimAmt(BigDecimal totalClaimAmt) {
        this.totalClaimAmt = totalClaimAmt;
    }

    @Column(name = "IS_OUTER_CONTAINER_DAMAGED")
    public Boolean getIsOuterContainerDamaged() {
        return isOuterContainerDamaged;
    }

    public void setIsOuterContainerDamaged(Boolean isOuterContainerDamaged) {
        this.isOuterContainerDamaged = isOuterContainerDamaged;
    }

    @Column(name = "IS_PACKAGE_DAMAGED")
    public Boolean getIsPackageDamaged() {
        return isPackageDamaged;
    }

    public void setIsPackageDamaged(Boolean isPackageDamaged) {
        this.isPackageDamaged = isPackageDamaged;
    }

    @Column(name = "IS_TWO_SUB_BOX")
    public Boolean getIsTwoSubBox() {
        return isTwoSubBox;
    }

    public void setIsTwoSubBox(Boolean isTwoSubBox) {
        this.isTwoSubBox = isTwoSubBox;
    }

    @Column(name = "IS_HAS_PRODUCT_RETURN")
    public Boolean getIsHasProductReturn() {
        return isHasProductReturn;
    }

    public void setIsHasProductReturn(Boolean isHasProductReturn) {
        this.isHasProductReturn = isHasProductReturn;
    }

    @Column(name = "IS_FILLED_WITH")
    public Boolean getIsFilledWith() {
        return isFilledWith;
    }

    public void setIsFilledWith(Boolean isFilledWith) {
        this.isFilledWith = isFilledWith;
    }

    @Column(name = "TRANS_NUMBER")
    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    @Column(name = "FILE_URL")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Column(name = "EXTRAL_REMARK")
    public String getExtralRemark() {
        return extralRemark;
    }

    public void setExtralRemark(String extralRemark) {
        this.extralRemark = extralRemark;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "LOGISTICS_DEPARTMENT_AMT")
    public Double getLogisticsepartmentAmt() {
        return logisticsepartmentAmt;
    }

    public void setLogisticsepartmentAmt(Double logisticsepartmentAmt) {
        this.logisticsepartmentAmt = logisticsepartmentAmt;
    }

    @Column(name = "EXPRESS_DELIVERY_AMT")
    public Double getExpressDeliveryAmt() {
        return expressDeliveryAmt;
    }

    public void setExpressDeliveryAmt(Double expressDeliveryAmt) {
        this.expressDeliveryAmt = expressDeliveryAmt;
    }

    @Column(name = "DISPOSE_REMARK")
    public String getDisposeRemark() {
        return disposeRemark;
    }

    public void setDisposeRemark(String disposeRemark) {
        this.disposeRemark = disposeRemark;
    }

    @Column(name = "SYSTEM_CODE")
    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    @Column(name = "DISPOSE_TIME")
    public Date getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Date disposeTime) {
        this.disposeTime = disposeTime;
    }

    @Column(name = "AFFIRM_TIME")
    public Date getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(Date affirmTime) {
        this.affirmTime = affirmTime;
    }

    @Column(name = "CLAIM_AFFIRM_STATUS")
    public Integer getClaimAffirmStatus() {
        return claimAffirmStatus;
    }

    public void setClaimAffirmStatus(Integer claimAffirmStatus) {
        this.claimAffirmStatus = claimAffirmStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_UPDATE_USER")
    public User getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(User lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }


	
}
