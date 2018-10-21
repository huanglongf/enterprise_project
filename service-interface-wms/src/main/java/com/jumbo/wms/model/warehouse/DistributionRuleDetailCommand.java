package com.jumbo.wms.model.warehouse;

/**
 *  配货规则维护
 *  @author NCGZ-DZ-SJ
 */
public class DistributionRuleDetailCommand extends DistributionRuleDetail {
	
	private static final long serialVersionUID = 6350392616436271260L;
	private Long l_conditionId;
	private Long l_ruleId;
	private String code;
	private String groupName;
	private String groupCode;
	private String name;
	private String l_kValue;
	private Integer intType;
	public Long getL_conditionId() {
		return l_conditionId;
	}
	public void setL_conditionId(Long l_conditionId) {
		this.l_conditionId = l_conditionId;
	}
	public Long getL_ruleId() {
		return l_ruleId;
	}
	public void setL_ruleId(Long l_ruleId) {
		this.l_ruleId = l_ruleId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getL_kValue() {
		return l_kValue;
	}
	public void setL_kValue(String l_kValue) {
		this.l_kValue = l_kValue;
	}
	public Integer getIntType() {
		return intType;
	}
	public void setIntType(Integer intType) {
		this.intType = intType;
	}
	
}
