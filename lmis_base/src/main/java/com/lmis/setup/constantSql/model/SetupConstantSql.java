package com.lmis.setup.constantSql.model;

import java.util.Date;
import java.util.List;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.getData.model.ConstantData;
import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SetupConstantSql
 * @Description: TODO(页面元素选择项)
 * @author Ian.Huang
 * @date 2017年12月4日 下午1:32:49 
 * 
 */
public class SetupConstantSql extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 8431718060800455439L;

	@ApiModelProperty(value = "sql编号，新增/修改/删除时必输")
	private String sqlCode;
	
	@ApiModelProperty(value = "sql名称，新增/修改时必输")
	private String sqlName;
	
	@ApiModelProperty(value = "sql语句，新增/修改/执行时必输")
	private String sqlRemark;
	
	@ApiModelProperty(value = "默认值")
	private String defaultValue;
	
	@ApiModelProperty(value = "是否有空选项")
	private Boolean isEmpty;
	
	@ApiModelProperty(value = "下拉框参数列表")
	private List<ConstantData> list;
	
	public SetupConstantSql() {}
	
	public SetupConstantSql(String id, Boolean isDeleted, String sqlCode) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.sqlCode = sqlCode;
	}
	
	public SetupConstantSql(String id, Date createTime, String createBy, Date updateTime, String updateBy, Boolean isDeleted,
			Boolean isDisabled, Integer version, String pwrOrg, String sqlCode, String sqlName, String sqlRemark) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.sqlCode = sqlCode;
		this.sqlName = sqlName;
		this.sqlRemark = sqlRemark;
	}

	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getSqlRemark() {
		return sqlRemark;
	}

	public void setSqlRemark(String sqlRemark) {
		this.sqlRemark = sqlRemark;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public List<ConstantData> getList() {
		return list;
	}

	public void setList(List<ConstantData> list) {
		this.list = list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString() {
		String str = "{id:" + super.getId()
		+ ",createTime:" + super.getCreateTime()
		+ ",createBy:" + super.getCreateBy()
		+ ",updateTime:" + super.getUpdateTime()
		+ ",updateBy:" + super.getUpdateBy()
		+ ",isDeleted:" + super.getIsDeleted()
		+ ",isDisabled:" + super.getIsDisabled()
		+ ",version:" + super.getVersion()
		+ ",pwrOrg:" + super.getPwrOrg()
		+ ",sqlCode:" + sqlCode
		+ ",sqlName:" + sqlName
		+ ",sqlRemark:" + sqlRemark
		+ ",defaultValue:" + defaultValue
		+ ",isEmpty:" + isEmpty
		+ ",list:";
		if(ObjectUtils.isNull(list)) {
			str += "null";
		} else {
			str += "[";
			for(int i=0; i<list.size(); i++) {
				str += list.get(i).toString();
				if((i + 1) != list.size()) {
					str += ",";
				}
			}
			str += "]";
		}
		str += "}";
		return str;
	}
	
}
