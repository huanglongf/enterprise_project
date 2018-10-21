package com.jumbo.wms.model.warehouse;

/**
 *  配货规则维护
 *  @author NCGZ-DZ-SJ
 */
public class DistributionRuleConditionCommand extends DistributionRuleCondition {
	
	private static final long serialVersionUID = 6350392616436271260L;
	private Long intId;
	private Integer intType;//状态
	
	
	public Long getIntId() {
		return intId;
	}
	public void setIntId(Long intId) {
		this.intId = intId;
	}
	public Integer getIntType() {
		return intType;
	}
	public void setIntType(Integer intType) {
		this.intType = intType;
	}
	
}
