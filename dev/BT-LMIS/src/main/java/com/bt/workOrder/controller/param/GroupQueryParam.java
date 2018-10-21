package com.bt.workOrder.controller.param;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:GroupQueryParam
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年2月9日下午1:30:31
 */
public class GroupQueryParam extends QueryParameter {
	private String queryType;
	private Integer id;
	private String group_code;
	private String group_name;
	private Integer superior;
	private Boolean autoAllocFlag;
	private String remark;
	private Boolean status;
	private Boolean dFlag;
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Integer getSuperior() {
		return superior;
	}
	public void setSuperior(Integer superior) {
		this.superior = superior;
	}
	public Boolean getAutoAllocFlag() {
		return autoAllocFlag;
	}
	public void setAutoAllocFlag(Boolean autoAllocFlag) {
		this.autoAllocFlag = autoAllocFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
	}
	
}
