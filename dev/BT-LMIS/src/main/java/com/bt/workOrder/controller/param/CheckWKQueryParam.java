package com.bt.workOrder.controller.param;

import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class CheckWKQueryParam extends QueryParameter{
	
	private Date stime;
	private Date etime;
	private String create_by;	//创建者
	private String 	processor; //处理者
	private String store; //店铺
	private String carrier; //物流服务商
	private String wo_type; //工单类型
	private String exception_type ; //异常类型 wo_error_type id
	private String express_number; //快递单号
	private String wo_no; //工单号
	
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getWo_type() {
		return wo_type;
	}
	public void setWo_type(String wo_type) {
		this.wo_type = wo_type;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public String getExpress_number() {
		return express_number;
	}
	public void setExpress_number(String express_number) {
		this.express_number = express_number;
	}
	public String getWo_no() {
		return wo_no;
	}
	public void setWo_no(String wo_no) {
		this.wo_no = wo_no;
	}
	
	
}
