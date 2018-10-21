package com.lmis.sys.codeRule.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysCoderuleData
 * @Description: TODO()
 * @author codeGenerator
 * @date 2018-05-22 09:01:05
 * 
 */
public class SysCoderuleData extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    @ApiModelProperty(value = "编码信息code")
	private String ruleCode;
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
    @ApiModelProperty(value = "规则配置表code")
	private String configCode;
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	
    @ApiModelProperty(value = "字段保存顺序")
	private Integer dataOrder;
	public Integer getDataOrder() {
		return dataOrder;
	}
	public void setDataOrder(Integer dataOrder) {
		this.dataOrder = dataOrder;
	}
	
    @ApiModelProperty(value = "起始值")
	private String startValue;
	public String getStartValue() {
		return startValue;
	}
	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}
	
    @ApiModelProperty(value = "增长值")
	private Integer increValue;
	public Integer getIncreValue() {
		return increValue;
	}
	public void setIncreValue(Integer increValue) {
		this.increValue = increValue;
	}
	
    @ApiModelProperty(value = "随机位数")
	private Integer number;
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
    @ApiModelProperty(value = "数据值长度")
	private Integer dataValuelg;
	public Integer getDataValuelg() {
		return dataValuelg;
	}
	public void setDataValuelg(Integer dataValuelg) {
		this.dataValuelg = dataValuelg;
	}
	
    @ApiModelProperty(value = "自增更新周期")
	private String updateCycle;
	public String getUpdateCycle() {
		return updateCycle;
	}
	public void setUpdateCycle(String updateCycle) {
		this.updateCycle = updateCycle;
	}
	
}
