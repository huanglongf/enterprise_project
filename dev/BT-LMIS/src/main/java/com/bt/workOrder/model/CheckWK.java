package com.bt.workOrder.model;

import java.math.BigDecimal;
import java.util.Date;

public class CheckWK {
	
	private String id; //主键
	private Date create_time ; //创建时间
	private String create_by; //创建人
	private Date update_time ; //更新时间
	private String update_by; //更新人
	private String wo_no; //工单号
	private String resource_wo_no; //来源工单号
	private String wo_source; //工单来源
	private String wo_alloc_status; //工单分配状态（WAA-待自动分配/WMA-待手动分配/ALD-已分配）
	private String wo_process_status; //工单处理状态（NEW-新建/PRO-处理中/PAU-挂起/CCD-已取消/FIN-已处理）
	private String wo_type; //工单类型
	private String wo_level; //工单级别
	private String exception_type ; //异常类型 wo_error_type id
	private String query_person; //查询人
	private String express_number; //快递单号
	private String carrier; //物流服务商
	private String logistics_status; //快递状态
	private String warning_type; //预警类型
	private String warning_level; //预警级别
	private String platform_number; //平台订单号
	private String related_number; //相关单据号
	private String store; //店铺
	private String order_amount ; //订单金额
	private String warehouse; //发货仓库
	private String transport_time; //出库时间
	private String recipient; //收件人
	private String phone; //联系电话
	private String address; //联系地址
	private String allocated_by; //分配者
	private String 	processor; //处理者
	private String process_content ; //主要处理内容说明
	private String pause_reason ; //挂起原因
	private String estimated_time_of_completion ; //预计完成时间
	private BigDecimal standard_manhours; //标准工时（分钟）
	private String process_start_point ; //处理起点时间
	private BigDecimal actual_manhours; //实际工时
	private String cal_date ; //时效计算时间
	private String watched; //1:升级监控中 0:取消升级监控
	
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
	public String getWo_no() {
		return wo_no;
	}
	public void setWo_no(String wo_no) {
		this.wo_no = wo_no;
	}
	public String getResource_wo_no() {
		return resource_wo_no;
	}
	public void setResource_wo_no(String resource_wo_no) {
		this.resource_wo_no = resource_wo_no;
	}
	public String getWo_source() {
		return wo_source;
	}
	public void setWo_source(String wo_source) {
		this.wo_source = wo_source;
	}
	public String getWo_alloc_status() {
		return wo_alloc_status;
	}
	public void setWo_alloc_status(String wo_alloc_status) {
		this.wo_alloc_status = wo_alloc_status;
	}
	public String getWo_process_status() {
		return wo_process_status;
	}
	public void setWo_process_status(String wo_process_status) {
		this.wo_process_status = wo_process_status;
	}
	public String getWo_type() {
		return wo_type;
	}
	public void setWo_type(String wo_type) {
		this.wo_type = wo_type;
	}
	public String getWo_level() {
		return wo_level;
	}
	public void setWo_level(String wo_level) {
		this.wo_level = wo_level;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public String getQuery_person() {
		return query_person;
	}
	public void setQuery_person(String query_person) {
		this.query_person = query_person;
	}
	public String getExpress_number() {
		return express_number;
	}
	public void setExpress_number(String express_number) {
		this.express_number = express_number;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getLogistics_status() {
		return logistics_status;
	}
	public void setLogistics_status(String logistics_status) {
		this.logistics_status = logistics_status;
	}
	public String getWarning_type() {
		return warning_type;
	}
	public void setWarning_type(String warning_type) {
		this.warning_type = warning_type;
	}
	public String getWarning_level() {
		return warning_level;
	}
	public void setWarning_level(String warning_level) {
		this.warning_level = warning_level;
	}
	public String getPlatform_number() {
		return platform_number;
	}
	public void setPlatform_number(String platform_number) {
		this.platform_number = platform_number;
	}
	public String getRelated_number() {
		return related_number;
	}
	public void setRelated_number(String related_number) {
		this.related_number = related_number;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getTransport_time() {
		return transport_time;
	}
	public void setTransport_time(String transport_time) {
		this.transport_time = transport_time;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAllocated_by() {
		return allocated_by;
	}
	public void setAllocated_by(String allocated_by) {
		this.allocated_by = allocated_by;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getProcess_content() {
		return process_content;
	}
	public void setProcess_content(String process_content) {
		this.process_content = process_content;
	}
	public String getPause_reason() {
		return pause_reason;
	}
	public void setPause_reason(String pause_reason) {
		this.pause_reason = pause_reason;
	}
	public String getEstimated_time_of_completion() {
		return estimated_time_of_completion;
	}
	public void setEstimated_time_of_completion(String estimated_time_of_completion) {
		this.estimated_time_of_completion = estimated_time_of_completion;
	}
	public BigDecimal getStandard_manhours() {
		return standard_manhours;
	}
	public void setStandard_manhours(BigDecimal standard_manhours) {
		this.standard_manhours = standard_manhours;
	}
	public String getProcess_start_point() {
		return process_start_point;
	}
	public void setProcess_start_point(String process_start_point) {
		this.process_start_point = process_start_point;
	}
	public BigDecimal getActual_manhours() {
		return actual_manhours;
	}
	public void setActual_manhours(BigDecimal actual_manhours) {
		this.actual_manhours = actual_manhours;
	}
	public String getCal_date() {
		return cal_date;
	}
	public void setCal_date(String cal_date) {
		this.cal_date = cal_date;
	}
	public String getWatched() {
		return watched;
	}
	public void setWatched(String watched) {
		this.watched = watched;
	}
}
