package com.bt.workOrder.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title:WorkTime
 * @Description: TODO(工时)
 * @author Ian.Huang 
 * @date 2017年1月5日下午6:05:54
 */
public class Manhours {
	private String id;
	private Date create_time;
	private String create_by;
	private Date update_time;
	private String update_by;
	private Integer account;
	private String date;
	private BigDecimal manhours;
	private BigDecimal allocated;
	private Boolean status;
	private String import_status;
	private String error_info;
	private String work_number;
	private String name;
	
	public String getWork_number() {
		return work_number;
	}
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImport_status() {
		return import_status;
	}
	public void setImport_status(String import_status) {
		this.import_status = import_status;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getManhours() {
		return manhours;
	}
	public void setManhours(BigDecimal manhours) {
		this.manhours = manhours;
	}
	public BigDecimal getAllocated() {
		return allocated;
	}
	public void setAllocated(BigDecimal allocated) {
		this.allocated = allocated;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
