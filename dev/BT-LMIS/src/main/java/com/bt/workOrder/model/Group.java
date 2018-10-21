package com.bt.workOrder.model;

import java.util.Date;

/**
 * @Title:Group
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年2月9日下午1:30:40
 */
public class Group{
	private Integer id;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	private String group_code;
	private String group_name;
	private Integer superior;
	private String remark;
	private String status;
	private String dFlag;
	private  String is_qa;

	public String getIs_qa() {
		return is_qa;
	}

	public void setIs_qa(String is_qa) {
		this.is_qa = is_qa;
	}

	public Group() {
		//　无参构造
		
	}
	
	public Group(Integer id, String update_by, String dFlag) {
		this.id= id;
		this.update_by= update_by;
		this.dFlag= dFlag;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getdFlag() {
		return dFlag;
	}
	public void setdFlag(String dFlag) {
		this.dFlag = dFlag;
	}
	
}
