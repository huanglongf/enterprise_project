package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import com.jumbo.wms.model.BaseModel;

/**
 *  条件明细表
 *  @author NCGZ-DZ-SJ
 */
@Entity
@Table(name = "T_WH_DSR_CONDITION")
public class DistributionRuleCondition extends BaseModel {
	
	private static final long serialVersionUID = 7639634949808988872L;
	private Long id;
	private String code;// 条件编码
	private String name;// 条件描述
	private String groupCode;// 条件组编码
	private String groupName;// 条件组描述
	private String kValue;// 条件值
	private DistributionRuleConditionType type;// 条件类型（chekbox或input..）


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DSR_CONDITION", sequenceName = "S_T_WH_DSR_CONDITION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DSR_CONDITION")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "GROUPCODE")
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "GROUPNAME")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "KVALUE")
	public String getkValue() {
		return kValue;
	}

	public void setkValue(String kValue) {
		this.kValue = kValue;
	}

	@Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.DistributionRuleConditionType")})
	public DistributionRuleConditionType getType() {
		return type;
	}

	public void setType(DistributionRuleConditionType type) {
		this.type = type;
	}
	
	
}
