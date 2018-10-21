package com.bt.radar.controller.form;
import com.bt.lmis.page.QueryParameter;

public class ExpressinfoMasterQueryParam extends QueryParameter {
	
		private String id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String waybill;			//快递单号	private String express_code;			//快递服务商代码	private String producttype_code;			//物流产品类型	private String work_no;			//作业单号	private String platform_no;			//平台订单号	private java.util.Date platform_time;			//	private String store_code;			//店铺代码	private String warehouse_code;			//揽件仓库代码（物理仓）	private String systemwh_code;			//系统仓代码	private java.util.Date embrace_time;			//揽件时间	private String shiptoname;			//收件人	private String phone;			//联系电话	private String address;			//收件地址	private String provice_code;			//目的省代码	private String city_code;			//目的市代码	private String state_code;			//目的区代码	private String street_code;			//目的街道代码	private String routestatus_code;			//快递状态代码	private String warningtype_code;			//预警类型代码	private Integer warning_level;			//预警级别	private Integer standard_efficiency;			//标准时效	private Integer real_efficiency;			//实际时效	private java.util.Date express_time;			//快递交接时间	private java.util.Date check_time;			//复核时间	private java.util.Date weight_time;			//称重时间	private java.util.Date embrance_time;			//实际揽件时间	private java.util.Date last_embrance_time;			//揽件截止时间
	private String status;			//运单状态	private String jkpoint;			//	private String warntype_code;
	private String warning_category;
	private String initial_level;
	private String  warining_status;
	private String start_time;
	private String end_time;
	private String del_flag;
	private String ebc_timestart;
	private String ebc_timeend;
	private String r_timestart;
	private String r_timeend;
	private String sr_timestart;
	private String sr_timeend;
	private String physical_warehouse_code;
	private String delivery_failure_reason;
	private java.util.Date delivery_failure_time;
	private String new_waybill;
	private String delivery_failure_start_time;
	public String getDelivery_failure_start_time() {
		return delivery_failure_start_time;
	}
	public void setDelivery_failure_start_time(String delivery_failure_start_time) {
		this.delivery_failure_start_time = delivery_failure_start_time;
	}
	public String getDelivery_failure_end_time() {
		return delivery_failure_end_time;
	}
	public void setDelivery_failure_end_time(String delivery_failure_end_time) {
		this.delivery_failure_end_time = delivery_failure_end_time;
	}
	private String delivery_failure_end_time;
	public String getPhysical_warehouse_code() {
		return physical_warehouse_code;
	}
	public String getDelivery_failure_reason() {
		return delivery_failure_reason;
	}
	public void setDelivery_failure_reason(String delivery_failure_reason) {
		this.delivery_failure_reason = delivery_failure_reason;
	}
	public java.util.Date getDelivery_failure_time() {
		return delivery_failure_time;
	}
	public void setDelivery_failure_time(java.util.Date delivery_failure_time) {
		this.delivery_failure_time = delivery_failure_time;
	}
	public String getNew_waybill() {
		return new_waybill;
	}
	public void setNew_waybill(String new_waybill) {
		this.new_waybill = new_waybill;
	}
	public void setPhysical_warehouse_code(String physical_warehouse_code) {
		this.physical_warehouse_code = physical_warehouse_code;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getWarining_status() {
		return warining_status;
	}
	public void setWarining_status(String warining_status) {
		this.warining_status = warining_status;
	}
	public String getWarning_category() {
		return warning_category;
	}
	public String getInitial_level() {
		return initial_level;
	}
	public void setInitial_level(String initial_level) {
		this.initial_level = initial_level;
	}
	public void setWarning_category(String warning_category) {
		this.warning_category = warning_category;
	}
	public String getId() {	    return this.id;	}	public String getWarntype_code() {
		return warntype_code;
	}
	public void setWarntype_code(String warntype_code) {
		this.warntype_code = warntype_code;
	}
	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getWaybill() {	    return this.waybill;	}	public void setWaybill(String waybill) {	    this.waybill=waybill;	}	public String getExpress_code() {	    return this.express_code;	}	public void setExpress_code(String express_code) {	    this.express_code=express_code;	}	public String getProducttype_code() {	    return this.producttype_code;	}	public void setProducttype_code(String producttype_code) {	    this.producttype_code=producttype_code;	}	public String getWork_no() {	    return this.work_no;	}	public void setWork_no(String work_no) {	    this.work_no=work_no;	}	public String getPlatform_no() {	    return this.platform_no;	}	public void setPlatform_no(String platform_no) {	    this.platform_no=platform_no;	}	public java.util.Date getPlatform_time() {	    return this.platform_time;	}	public void setPlatform_time(java.util.Date platform_time) {	    this.platform_time=platform_time;	}	public String getStore_code() {	    return this.store_code;	}	public void setStore_code(String store_code) {	    this.store_code=store_code;	}	public String getWarehouse_code() {	    return this.warehouse_code;	}	public void setWarehouse_code(String warehouse_code) {	    this.warehouse_code=warehouse_code;	}	public String getSystemwh_code() {	    return this.systemwh_code;	}	public void setSystemwh_code(String systemwh_code) {	    this.systemwh_code=systemwh_code;	}	public java.util.Date getEmbrace_time() {	    return this.embrace_time;	}	public void setEmbrace_time(java.util.Date embrace_time) {	    this.embrace_time=embrace_time;	}	public String getShiptoname() {	    return this.shiptoname;	}	public void setShiptoname(String shiptoname) {	    this.shiptoname=shiptoname;	}	public String getPhone() {	    return this.phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public String getAddress() {	    return this.address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getProvice_code() {	    return this.provice_code;	}	public void setProvice_code(String provice_code) {	    this.provice_code=provice_code;	}	public String getCity_code() {	    return this.city_code;	}	public void setCity_code(String city_code) {	    this.city_code=city_code;	}	public String getState_code() {	    return this.state_code;	}	public void setState_code(String state_code) {	    this.state_code=state_code;	}	public String getStreet_code() {	    return this.street_code;	}	public void setStreet_code(String street_code) {	    this.street_code=street_code;	}	public String getRoutestatus_code() {	    return this.routestatus_code;	}	public void setRoutestatus_code(String routestatus_code) {	    this.routestatus_code=routestatus_code;	}	public String getWarningtype_code() {	    return this.warningtype_code;	}	public void setWarningtype_code(String warningtype_code) {	    this.warningtype_code=warningtype_code;	}	public Integer getWarning_level() {	    return this.warning_level;	}	public void setWarning_level(Integer warning_level) {	    this.warning_level=warning_level;	}	public Integer getStandard_efficiency() {	    return this.standard_efficiency;	}	public void setStandard_efficiency(Integer standard_efficiency) {	    this.standard_efficiency=standard_efficiency;	}	public Integer getReal_efficiency() {	    return this.real_efficiency;	}	public void setReal_efficiency(Integer real_efficiency) {	    this.real_efficiency=real_efficiency;	}	public java.util.Date getExpress_time() {	    return this.express_time;	}	public void setExpress_time(java.util.Date express_time) {	    this.express_time=express_time;	}	public java.util.Date getCheck_time() {	    return this.check_time;	}	public void setCheck_time(java.util.Date check_time) {	    this.check_time=check_time;	}	public java.util.Date getWeight_time() {	    return this.weight_time;	}	public void setWeight_time(java.util.Date weight_time) {	    this.weight_time=weight_time;	}	public java.util.Date getEmbrance_time() {	    return this.embrance_time;	}	public void setEmbrance_time(java.util.Date embrance_time) {	    this.embrance_time=embrance_time;	}	public java.util.Date getLast_embrance_time() {	    return this.last_embrance_time;	}	public void setLast_embrance_time(java.util.Date last_embrance_time) {	    this.last_embrance_time=last_embrance_time;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public String getJkpoint() {	    return this.jkpoint;	}	public void setJkpoint(String jkpoint) {	    this.jkpoint=jkpoint;	}
	public String getEbc_timestart() {
		return ebc_timestart;
	}
	public void setEbc_timestart(String ebc_timestart) {
		this.ebc_timestart = ebc_timestart;
	}
	public String getEbc_timeend() {
		return ebc_timeend;
	}
	public void setEbc_timeend(String ebc_timeend) {
		this.ebc_timeend = ebc_timeend;
	}
	public String getR_timestart() {
		return r_timestart;
	}
	public void setR_timestart(String r_timestart) {
		this.r_timestart = r_timestart;
	}
	public String getR_timeend() {
		return r_timeend;
	}
	public void setR_timeend(String r_timeend) {
		this.r_timeend = r_timeend;
	}
	public String getSr_timestart() {
		return sr_timestart;
	}
	public void setSr_timestart(String sr_timestart) {
		this.sr_timestart = sr_timestart;
	}
	public String getSr_timeend() {
		return sr_timeend;
	}
	public void setSr_timeend(String sr_timeend) {
		this.sr_timeend = sr_timeend;
	}
	
}
