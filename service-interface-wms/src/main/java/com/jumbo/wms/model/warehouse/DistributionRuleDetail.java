package com.jumbo.wms.model.warehouse;


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

import com.jumbo.wms.model.BaseModel;

/**
 *  配货规则明细表
 *  @author NCGZ-DZ-SJ
 */
@Entity
@Table(name = "T_WH_DSR_DETAIL")
public class DistributionRuleDetail extends BaseModel {
	
	private static final long serialVersionUID = -6480822189528671515L;
	
	private Long id;
	
	private DistributionRuleCondition conditionId;// 条件ID
	
	private String remark;// 内容
	
	private DistributionRule ruleId;// 配货规则ID
    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DSR_DETAIL", sequenceName = "S_T_WH_DSR_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DSR_DETAIL")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONDITION_ID")
	public DistributionRuleCondition getConditionId() {
		return conditionId;
	}

	public void setConditionId(DistributionRuleCondition conditionId) {
		this.conditionId = conditionId;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RULE_ID")
	public DistributionRule getRuleId() {
		return ruleId;
	}

	public void setRuleId(DistributionRule ruleId) {
		this.ruleId = ruleId;
	}
	
}
