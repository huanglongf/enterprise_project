package com.bt.radar.model;

import java.math.BigDecimal;

/**
* @ClassName: ExpressinfoMaster
* @Description: TODO(ExpressinfoMaster)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class ExpressinfoMaster {
	
		private String id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String waybill;			//快递单号	private String express_code;			//快递服务商代码	private String producttype_code;			//物流产品类型	private String work_no;			//作业单号	private String platform_no;			//平台订单号	private java.util.Date platform_time;			//	private String store_code;			//店铺代码	private String warehouse_code;			//揽件仓库代码（物理仓）	private String systemwh_code;			//系统仓代码	private java.util.Date embrace_time;			//揽件时间	private String shiptoname;			//收件人	private String phone;			//联系电话	private String address;			//收件地址	private String provice_code;			//目的省代码	private String city_code;			//目的市代码	private String state_code;			//目的区代码	private String street_code;			//目的街道代码	private String routestatus_code;			//快递状态代码	private String warningtype_code;			//预警类型代码	private Integer warning_level;			//预警级别	private Integer standard_efficiency;			//标准时效	private Integer real_efficiency;			//实际时效	private java.util.Date express_time;			//快递交接时间	private java.util.Date check_time;			//复核时间	private java.util.Date weight_time;			//称重时间	private java.util.Date embrance_time;			//实际揽件时间	private java.util.Date last_embrance_time;			//揽件截止时间	private String status;			//运单状态	private String jkpoint;			//	private String hid;
	private String transport_name;
	private String product_type_name;
	private String store_name;
	private String warehouse_name;
	private String warningtype_name;
	private String del_flag;
	private String express_name;
	private String producttype_name;
	private String systemwh_name;
	private BigDecimal order_amount;
	private String related_number;
	private String bat_id;
	private String delivery_failure_reason;
	private java.util.Date delivery_failure_time;
	private String new_waybill;
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getProducttype_name() {
		return producttype_name;
	}
	public void setProducttype_name(String producttype_name) {
		this.producttype_name = producttype_name;
	}
	public String getSystemwh_name() {
		return systemwh_name;
	}
	public void setSystemwh_name(String systemwh_name) {
		this.systemwh_name = systemwh_name;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getWarningtype_name() {
		return warningtype_name;
	}
	public void setWarningtype_name(String warningtype_name) {
		this.warningtype_name = warningtype_name;
	}
	public String getTransport_name() {
		return transport_name;
	}
	public void setTransport_name(String transport_name) {
		this.transport_name = transport_name;
	}
	public String getProduct_type_name() {
		return product_type_name;
	}
	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getProvice_name() {
		return provice_name;
	}
	public void setProvice_name(String provice_name) {
		this.provice_name = provice_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	private String provice_name;
	private String city_name;
	private String state_name;
	private String street_name;
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getId(){	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getWaybill() {	    return this.waybill;	}	public void setWaybill(String waybill) {	    this.waybill=waybill;	}	public String getExpress_code() {	    return this.express_code;	}	public void setExpress_code(String express_code) {	    this.express_code=express_code;	}	public String getProducttype_code() {	    return this.producttype_code;	}	public void setProducttype_code(String producttype_code) {	    this.producttype_code=producttype_code;	}	public String getWork_no() {	    return this.work_no;	}	public void setWork_no(String work_no) {	    this.work_no=work_no;	}	public String getPlatform_no() {	    return this.platform_no;	}	public void setPlatform_no(String platform_no) {	    this.platform_no=platform_no;	}	public java.util.Date getPlatform_time() {	    return this.platform_time;	}	public void setPlatform_time(java.util.Date platform_time) {	    this.platform_time=platform_time;	}	public String getStore_code() {	    return this.store_code;	}	public void setStore_code(String store_code) {	    this.store_code=store_code;	}	public String getWarehouse_code() {	    return this.warehouse_code;	}	public void setWarehouse_code(String warehouse_code) {	    this.warehouse_code=warehouse_code;	}	public String getSystemwh_code() {	    return this.systemwh_code;	}	public void setSystemwh_code(String systemwh_code) {	    this.systemwh_code=systemwh_code;	}	public java.util.Date getEmbrace_time() {	    return this.embrace_time;	}	public void setEmbrace_time(java.util.Date embrace_time) {	    this.embrace_time=embrace_time;	}	public String getShiptoname() {	    return this.shiptoname;	}	public void setShiptoname(String shiptoname) {	    this.shiptoname=shiptoname;	}	public String getPhone() {	    return this.phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public String getAddress() {	    return this.address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getProvice_code() {	    return this.provice_code;	}	public void setProvice_code(String provice_code) {	    this.provice_code=provice_code;	}	public String getCity_code() {	    return this.city_code;	}	public void setCity_code(String city_code) {	    this.city_code=city_code;	}	public String getState_code() {	    return this.state_code;	}	public void setState_code(String state_code) {	    this.state_code=state_code;	}	public String getStreet_code() {	    return this.street_code;	}	public void setStreet_code(String street_code) {	    this.street_code=street_code;	}	public String getRoutestatus_code() {	    return this.routestatus_code;	}	public void setRoutestatus_code(String routestatus_code) {	    this.routestatus_code=routestatus_code;	}	public String getWarningtype_code() {	    return this.warningtype_code;	}	public void setWarningtype_code(String warningtype_code) {	    this.warningtype_code=warningtype_code;	}	public Integer getWarning_level() {	    return this.warning_level;	}	public void setWarning_level(Integer warning_level) {	    this.warning_level=warning_level;	}	public Integer getStandard_efficiency() {	    return this.standard_efficiency;	}	public void setStandard_efficiency(Integer standard_efficiency) {	    this.standard_efficiency=standard_efficiency;	}	public Integer getReal_efficiency() {	    return this.real_efficiency;	}	public void setReal_efficiency(Integer real_efficiency) {	    this.real_efficiency=real_efficiency;	}	public java.util.Date getExpress_time() {	    return this.express_time;	}	public void setExpress_time(java.util.Date express_time) {	    this.express_time=express_time;	}	public java.util.Date getCheck_time() {	    return this.check_time;	}	public void setCheck_time(java.util.Date check_time) {	    this.check_time=check_time;	}	public java.util.Date getWeight_time() {	    return this.weight_time;	}	public void setWeight_time(java.util.Date weight_time) {	    this.weight_time=weight_time;	}	public java.util.Date getEmbrance_time() {	    return this.embrance_time;	}	public void setEmbrance_time(java.util.Date embrance_time) {	    this.embrance_time=embrance_time;	}	public java.util.Date getLast_embrance_time() {	    return this.last_embrance_time;	}	public void setLast_embrance_time(java.util.Date last_embrance_time) {	    this.last_embrance_time=last_embrance_time;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public String getJkpoint() {	    return this.jkpoint;	}	public void setJkpoint(String jkpoint) {	    this.jkpoint=jkpoint;	}
	public BigDecimal getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(BigDecimal order_amount) {
		this.order_amount = order_amount;
	}
	public String getRelated_number() {
		return related_number;
	}
	public void setRelated_number(String related_number) {
		this.related_number = related_number;
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
}
