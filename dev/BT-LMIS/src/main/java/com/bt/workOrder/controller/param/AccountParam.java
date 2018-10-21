package com.bt.workOrder.controller.param;

import com.bt.lmis.page.QueryParameter;

/**
 * @Title:AccountParam
 * @Description: TODO 
 * @author Ian.Huang 
 * @date 2017年1月7日下午5:52:12
 */
public class AccountParam extends QueryParameter {
	
	private String group_name;
	private String employee_number;
	private String name;
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getEmployee_number() {
		return employee_number;
	}
	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
