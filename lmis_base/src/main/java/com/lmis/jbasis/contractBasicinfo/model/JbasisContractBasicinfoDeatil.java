package com.lmis.jbasis.contractBasicinfo.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: JbasisContractBasicinfoDeatil
 * @Description: TODO(计费协议明细)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 */
public class JbasisContractBasicinfoDeatil extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "合同编码")
	private String contractNo;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
    @ApiModelProperty(value = "计费类型编码")
	private String typeCode;
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
    @ApiModelProperty(value = "收支类型")
	private String incomeandexpType;
	public String getIncomeandexpType() {
		return incomeandexpType;
	}
	public void setIncomeandexpType(String incomeandexpType) {
		this.incomeandexpType = incomeandexpType;
	}
	
    @ApiModelProperty(value = "计费规则组编码")
	private String ruleCode;
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
    @ApiModelProperty(value = "是否结算 0 否 1 是")
	private String isBalance;
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	
    @ApiModelProperty(value = "是否预估 0 否 1 是")
	private String isEstimate;
	public String getIsEstimate() {
		return isEstimate;
	}
	public void setIsEstimate(String isEstimate) {
		this.isEstimate = isEstimate;
	}
	
}
