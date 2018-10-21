package com.lmis.jbasis.contractBasicinfo.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: ViewJbasisContractBasicinfoDeatil
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 */
public class ViewJbasisContractBasicinfoDeatil{

    @ApiModelProperty(value = "主键")
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @ApiModelProperty(value = "创建人")
    private String createBy;

    public String getCreateBy(){
        return createBy;
    }

    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    public String getUpdateBy(){
        return updateBy;
    }

    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    @ApiModelProperty(value = "1 是删除 0未删除")
    private Boolean isDeleted;

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    @ApiModelProperty(value = "0是启用 1是禁用")
    private Boolean isDisabled;

    public Boolean getIsDisabled(){
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled){
        this.isDisabled = isDisabled;
    }

    @ApiModelProperty(value = "版本")
    private Integer version;

    public Integer getVersion(){
        return version;
    }

    public void setVersion(Integer version){
        this.version = version;
    }

    @ApiModelProperty(value = "权限")
    private String pwrOrg;

    public String getPwrOrg(){
        return pwrOrg;
    }

    public void setPwrOrg(String pwrOrg){
        this.pwrOrg = pwrOrg;
    }

    @ApiModelProperty(value = "合同编码")
    private String contractNo;

    public String getContractNo(){
        return contractNo;
    }

    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }

    @ApiModelProperty(value = "计费类型编码")
    private String typeCode;

    public String getTypeCode(){
        return typeCode;
    }

    public void setTypeCode(String typeCode){
        this.typeCode = typeCode;
    }

    @ApiModelProperty(value = "")
    private String typeCodeDisplay;

    public String getTypeCodeDisplay(){
        return typeCodeDisplay;
    }

    public void setTypeCodeDisplay(String typeCodeDisplay){
        this.typeCodeDisplay = typeCodeDisplay;
    }

    @ApiModelProperty(value = "收支类型")
    private String incomeandexpType;

    public String getIncomeandexpType(){
        return incomeandexpType;
    }

    public void setIncomeandexpType(String incomeandexpType){
        this.incomeandexpType = incomeandexpType;
    }

    @ApiModelProperty(value = "")
    private String incomeandexpTypeDisplay;

    public String getIncomeandexpTypeDisplay(){
        return incomeandexpTypeDisplay;
    }

    public void setIncomeandexpTypeDisplay(String incomeandexpTypeDisplay){
        this.incomeandexpTypeDisplay = incomeandexpTypeDisplay;
    }

    @ApiModelProperty(value = "计费规则组编码")
    private String ruleCode;

    public String getRuleCode(){
        return ruleCode;
    }

    public void setRuleCode(String ruleCode){
        this.ruleCode = ruleCode;
    }

    @ApiModelProperty(value = "")
    private String ruleCodeDisplay;

    public String getRuleCodeDisplay(){
        return ruleCodeDisplay;
    }

    public void setRuleCodeDisplay(String ruleCodeDisplay){
        this.ruleCodeDisplay = ruleCodeDisplay;
    }

    @ApiModelProperty(value = "是否结算 0 否 1 是")
    private String isBalance;

    public String getIsBalance(){
        return isBalance;
    }

    public void setIsBalance(String isBalance){
        this.isBalance = isBalance;
    }

    @ApiModelProperty(value = "")
    private String isBalanceDisplay;

    public String getIsBalanceDisplay(){
        return isBalanceDisplay;
    }

    public void setIsBalanceDisplay(String isBalanceDisplay){
        this.isBalanceDisplay = isBalanceDisplay;
    }

    @ApiModelProperty(value = "是否预估 0 否 1 是")
    private String isEstimate;

    public String getIsEstimate(){
        return isEstimate;
    }

    public void setIsEstimate(String isEstimate){
        this.isEstimate = isEstimate;
    }

    @ApiModelProperty(value = "")
    private String isEstimateDisplay;

    public String getIsEstimateDisplay(){
        return isEstimateDisplay;
    }

    public void setIsEstimateDisplay(String isEstimateDisplay){
        this.isEstimateDisplay = isEstimateDisplay;
    }

}
