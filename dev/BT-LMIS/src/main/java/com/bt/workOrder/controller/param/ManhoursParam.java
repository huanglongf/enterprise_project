package com.bt.workOrder.controller.param;

import com.bt.lmis.page.QueryParameter;

/**
 * @Title:ManhoursParam
 * @Description: TODO(工时查询参数)
 * @author Ian.Huang 
 * @date 2017年1月5日下午4:43:23
 */
public class ManhoursParam extends QueryParameter {
	
	private String employee_number;
	private String employee_name;
	private String date;
	private String date_start;
	private String date_end;
	public String getEmployee_number() {
		return employee_number;
	}
	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	
}
