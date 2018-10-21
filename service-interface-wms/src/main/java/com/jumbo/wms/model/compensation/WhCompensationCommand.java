package com.jumbo.wms.model.compensation;

import java.util.Date;


/**
 * 索赔单
 * 
 * @author lihui
 */
public class WhCompensationCommand extends WhCompensation {

    /**
     * 
     */
    private static final long serialVersionUID = -2896449895451212795L;

    /**
     * 索赔分类名称
     */
    private String claimTypeName;

    /**
     * 索赔分类
     */
    private String claimTypeId;

    /**
     * 索赔原因名称
     */
    private String claimReasonNmae;

    /**
     * 索赔原因
     */
    private String claimReasonId;

    /**
     * 承担方
     */
    private String bearTarget;

    private Date startDate;

    private Date endDate;

    private String transName;

    private Integer claimStatus;

    private String receiverAddress;

    private String mobile;

    private String receiver;

    private String warehouseName;

    private Long warehouseId;

    private String userName;

    private Date staFinishDate;

    private Date staFinishDateStart;

    private Date staFinishDateEnd;

    /**
     * 商品编码
     */
    private String commodityCode;
    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品耗材
     */
    private String material;
    
    /**
     * 关联作业单ID
     */
    private Long staId;


    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * 预警天数
     */
    private Integer warn;



    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getClaimTypeName() {
        return claimTypeName;
    }

    public void setClaimTypeName(String claimTypeName) {
        this.claimTypeName = claimTypeName;
    }

    public String getClaimReasonNmae() {
        return claimReasonNmae;
    }

    public void setClaimReasonNmae(String claimReasonNmae) {
        this.claimReasonNmae = claimReasonNmae;
    }

    public String getBearTarget() {
        return bearTarget;
    }

    public void setBearTarget(String bearTarget) {
        this.bearTarget = bearTarget;
    }

    public String getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(String claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public String getClaimReasonId() {
        return claimReasonId;
    }

    public void setClaimReasonId(String claimReasonId) {
        this.claimReasonId = claimReasonId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Integer getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(Integer claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStaFinishDate() {
        return staFinishDate;
    }

    public void setStaFinishDate(Date staFinishDate) {
        this.staFinishDate = staFinishDate;
    }

    public Date getStaFinishDateStart() {
        return staFinishDateStart;
    }

    public void setStaFinishDateStart(Date staFinishDateStart) {
        this.staFinishDateStart = staFinishDateStart;
    }

    public Date getStaFinishDateEnd() {
        return staFinishDateEnd;
    }

    public void setStaFinishDateEnd(Date staFinishDateEnd) {
        this.staFinishDateEnd = staFinishDateEnd;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
