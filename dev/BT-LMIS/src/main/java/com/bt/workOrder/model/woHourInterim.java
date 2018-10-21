package com.bt.workOrder.model;

public class woHourInterim {
    private int id;
    private String import_status;
    private String error_info;
    private String work_number;
    private String name;
    private String data_time;
    private String man_hour;
    private String to_delete;
	public String getTo_delete() {
		return to_delete;
	}
	public void setTo_delete(String to_delete) {
		this.to_delete = to_delete;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getData_time() {
		return data_time;
	}
	public void setData_time(String data_time) {
		this.data_time = data_time;
	}
	public String getMan_hour() {
		return man_hour;
	}
	public void setMan_hour(String man_hour) {
		this.man_hour = man_hour;
	}
}
