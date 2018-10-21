package com.lmis.setup.constant.model;

import java.util.Date;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
public class SetupConstant extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = -2861269747763676631L;
	
	@ApiModelProperty(value = "常量编号，新增/修改/删除时必输")
	private String constantCode;

	@ApiModelProperty(value = "常量名称，新增/修改时必输")
	private String constantName;

	@ApiModelProperty(value = "分组编号，新增/修改时必输")
	private String groupCode;

	@ApiModelProperty(value = "分组名称，新增/修改时必输")
	private String groupName;

	@ApiModelProperty(value = "显示顺序")
	private Integer constantSeq;

	@ApiModelProperty(value = "常量名称1，用于国际化")
	private String constantName1;

	@ApiModelProperty(value = "常量名称2，用于国际化")
	private String constantName2;

	@ApiModelProperty(value = "备注")
	private String remark;

	public SetupConstant() {}
	
	public SetupConstant(String id, Boolean isDeleted, String constantCode) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.constantCode = constantCode;
	}
	
	public SetupConstant(String id, Date createTime, String createBy, Date updateTime, String updateBy,
			Boolean isDeleted, Boolean isDisabled, Integer version, String pwrOrg, String constantCode,
			String constantName, String groupCode, String groupName) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.constantCode = constantCode;
		this.constantName = constantName;
		this.groupCode = groupCode;
		this.groupName = groupName;
	}
	
	public String getConstantCode() {
		return constantCode;
	}

	public void setConstantCode(String constantCode) {
		this.constantCode = constantCode;
	}

	public String getConstantName() {
		return constantName;
	}

	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getConstantSeq() {
		return constantSeq;
	}

	public void setConstantSeq(Integer constantSeq) {
		this.constantSeq = constantSeq;
	}

	public String getConstantName1() {
		return constantName1;
	}

	public void setConstantName1(String constantName1) {
		this.constantName1 = constantName1;
	}

	public String getConstantName2() {
		return constantName2;
	}

	public void setConstantName2(String constantName2) {
		this.constantName2 = constantName2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString() {
		return "{id:" + super.getId()
		+ ",createTime:" + super.getCreateTime()
		+ ",createBy:" + super.getCreateBy()
		+ ",updateTime:" + super.getUpdateTime()
		+ ",updateBy:" + super.getUpdateBy()
		+ ",isDeleted:" + super.getIsDeleted()
		+ ",isDisabled:" + super.getIsDisabled()
		+ ",version:" + super.getVersion()
		+ ",pwrOrg:" + super.getPwrOrg()
		+ ",constantCode:" + constantCode
		+ ",constantName:" + constantName
		+ ",groupCode:" + groupCode
		+ ",groupName:" + groupName
		+ ",constantSeq:" + constantSeq
		+ ",constantName1:" + constantName1
		+ ",constantName2:" + constantName2
		+ ",remark:" + remark
		+"}";
	}
	
}
