package com.bt.orderPlatform.controller.form;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.bt.orderPlatform.page.QueryParameter;

public class WaybillMasterQueryParam extends QueryParameter {
	
		private String id;							//
	private String order_id;    				//订单号
	private java.util.Date create_time;			//系统创建时间
	private String create_user;					//创建人
	private java.util.Date update_time;			//系统更新时间
	private String update_user;					//修改人
	private String waybill;						//运单号
	private String from_orgnization;			//发货机构
	private String to_orgnization;				//收货机构
	private String status;						//状态代码 初始值为0
	private String producttype_code;			//业务类型代码
	private String producttype_name;			//业务类型名称
	private String to_phone;					//收货人电话
	private String to_contacts;					//收货联系人
	private String to_address;					//收货地址
	private String to_province;					//收货地省
	private String to_city;						//收货地市
	private String to_state;					//收货地区
	
	private String to_street;					//收货地街道
	private String from_contacts;				//发货联系人
	private String from_contacts_like;
	private String to_contacts_like;
	private String from_phone;					//发货人电话
	private String from_address;				//发货地址
	private String from_province;				//发货地省
	private String from_city;					//发货地市
	private String from_state;					//发货地区

	private String from_street;					//发货地街道
	private java.util.Date order_time;			//下单日期
	private String route_status;				//路由状态
	private Integer total_qty;					//总件数，对应订单号有几个
	private java.math.BigDecimal total_weight;	//总毛重(kg)
	private java.math.BigDecimal total_volumn;	//总体积(立方厘米)
	private java.math.BigDecimal total_amount;	//总费用
	
	private String isAutoBill;
	private String payment;
	private String expressCode;
	private String to_phone2;//收件人电话2
	private String customer_phone;//全民付注册手机号
	private String customer_source;//渠道来源:01全民付，02收银台，03网服，04微信
	private String submit_time;//预约时间(11:12:23)
	private String submit_date;//预约日期(2015-11-19)
	private String memo;//备注
	private String cargo_type;//货物类型
	private String to_num;//收件人地址编码
	private String from_num;//寄件人地址编码
	private String customer_number;//上游客户单号
	private String ess_time;//起始时间客
	private String s_time;//起始时间客
	private String ese_time;//结束时间
	private String e_time;//结束时间
	private String query_test;//模糊条件
	private String mark_destination;//大头笔
	private String package_name;//集包地名称
	private String package_code;//集包地名称
	private String to_province_code;					//收货地省
	private String to_city_code;						//收货地市
	private String to_state_code;					//收货地区
	private String from_province_code;				//发货地省
	private String from_city_code;					//发货地市
	private String from_state_code;					//发货地区
	private String from_orgnization_code;					//发货地区
	private String crrier;
	private String crrier_type;
	private String org_cde;
	private String userid;
	private String username;
	private String wh_code;
	private String express_name;
	private String print_code;	
	private Date print_time;	
	private String amount_flag;	
	private String sendcode;	
	private String is_docall;	
	private String lje_time;					//揽件时间
	private String ljs_time;					//揽件时间
	private String start_date;					//揽件时间
	private Integer new_source;					//揽件时间
	private  String custid;   //月结卡号
	private  String pay_path;   //支付方式
	private  String cost_center;   //成本中心
	private  String outStatus;   //记录状态是否存在
	private  String bat_id;   //导入的批次号
	private  String place_error;   //下单错误原因
	private  java.math.BigDecimal insured;   //下单错误原因
	private  String store_code;   //店铺code
	private  String expected_from_dateS;   //预计发货时间
	private  String expected_from_dateE;   //预计发货时间
	
	
	public String getExpected_from_dateS() {
		return expected_from_dateS;
	}
	public void setExpected_from_dateS(String expected_from_dateS) {
		this.expected_from_dateS = expected_from_dateS;
	}
	public String getExpected_from_dateE() {
		return expected_from_dateE;
	}
	public void setExpected_from_dateE(String expected_from_dateE) {
		this.expected_from_dateE = expected_from_dateE;
	}
	public java.math.BigDecimal getInsured() {
		return insured;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public void setInsured(java.math.BigDecimal insured) {
		this.insured = insured;
	}
	public String getPlace_error() {
		return place_error;
	}
	public void setPlace_error(String place_error) {
		this.place_error = place_error;
	}
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public String getOutStatus() {
		return outStatus;
	}
	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}
	public String getPay_path() {
		return pay_path;
	}
	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}
	public String getCost_center() {
		return cost_center;
	}
	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public Integer getNew_source() {
		return new_source;
	}
	public void setNew_source(Integer new_source) {
		this.new_source = new_source;
	}
	public String getLje_time() {
		return lje_time;
	}
	public void setLje_time(String lje_time) {
		this.lje_time = lje_time;
	}
	public String getLjs_time() {
		return ljs_time;
	}
	public void setLjs_time(String ljs_time) {
		this.ljs_time = ljs_time;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getIs_docall() {
		return is_docall;
	}
	public void setIs_docall(String is_docall) {
		this.is_docall = is_docall;
	}
	public String getSendcode() {
		return sendcode;
	}
	public void setSendcode(String sendcode) {
		this.sendcode = sendcode;
	}
	public String getAmount_flag() {
		return amount_flag;
	}
	public void setAmount_flag(String amount_flag) {
		this.amount_flag = amount_flag;
	}
	public Date getPrint_time() {
		return print_time;
	}
	public void setPrint_time(Date print_time) {
		this.print_time = print_time;
	}
	public String getEss_time() {
		return ess_time;
	}
	public void setEss_time(String ess_time) {
		this.ess_time = ess_time;
	}
	public String getEse_time() {
		return ese_time;
	}
	public void setEse_time(String ese_time) {
		this.ese_time = ese_time;
	}
	public String getPrint_code() {
		return print_code;
	}
	public void setPrint_code(String print_code) {
		this.print_code = print_code;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getCrrier() {
		return crrier;
	}
	public void setCrrier(String crrier) {
		this.crrier = crrier;
	}
	public String getCrrier_type() {
		return crrier_type;
	}
	public void setCrrier_type(String crrier_type) {
		this.crrier_type = crrier_type;
	}
	public String getOrg_cde() {
		return org_cde;
	}
	public void setOrg_cde(String org_cde) {
		this.org_cde = org_cde;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWh_code() {
		return wh_code;
	}
	public void setWh_code(String wh_code) {
		this.wh_code = wh_code;
	}
	public String getFrom_orgnization_code() {
		return from_orgnization_code;
	}
	public void setFrom_orgnization_code(String from_orgnization_code) {
		this.from_orgnization_code = from_orgnization_code;
	}
	public String getTo_province_code() {
		return to_province_code;
	}
	public void setTo_province_code(String to_province_code) {
		this.to_province_code = to_province_code;
	}
	public String getTo_city_code() {
		return to_city_code;
	}
	public void setTo_city_code(String to_city_code) {
		this.to_city_code = to_city_code;
	}
	public String getTo_state_code() {
		return to_state_code;
	}
	public void setTo_state_code(String to_state_code) {
		this.to_state_code = to_state_code;
	}
	public String getFrom_province_code() {
		return from_province_code;
	}
	public void setFrom_province_code(String from_province_code) {
		this.from_province_code = from_province_code;
	}
	public String getFrom_city_code() {
		return from_city_code;
	}
	public void setFrom_city_code(String from_city_code) {
		this.from_city_code = from_city_code;
	}
	public String getFrom_state_code() {
		return from_state_code;
	}
	public void setFrom_state_code(String from_state_code) {
		this.from_state_code = from_state_code;
	}
	public String getMark_destination() {
		return mark_destination;
	}
	public void setMark_destination(String mark_destination) {
		this.mark_destination = mark_destination;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public String getPackage_code() {
		return package_code;
	}
	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}
	public String getQuery_test() {
		return query_test;
	}
	public void setQuery_test(String query_test) {
		this.query_test = query_test;
	}
	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	public String getE_time() {
		return e_time;
	}
	public void setE_time(String e_time) {
		this.e_time = e_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCustomer_number() {
		return customer_number;
	}
	public void setCustomer_number(String customer_number) {
		this.customer_number = customer_number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.util.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public java.util.Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(java.util.Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getFrom_orgnization() {
		return from_orgnization;
	}
	public void setFrom_orgnization(String from_orgnization) {
		this.from_orgnization = from_orgnization;
	}
	public String getTo_orgnization() {
		return to_orgnization;
	}
	public void setTo_orgnization(String to_orgnization) {
		this.to_orgnization = to_orgnization;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProducttype_code() {
		return producttype_code;
	}
	public void setProducttype_code(String producttype_code) {
		this.producttype_code = producttype_code;
	}
	public String getProducttype_name() {
		return producttype_name;
	}
	public void setProducttype_name(String producttype_name) {
		this.producttype_name = producttype_name;
	}
	public String getTo_phone() {
		return to_phone;
	}
	public void setTo_phone(String to_phone) {
		this.to_phone = to_phone;
	}
	public String getTo_contacts() {
		return to_contacts;
	}
	public void setTo_contacts(String to_contacts) {
		this.to_contacts = to_contacts;
	}
	public String getTo_address() {
		return to_address;
	}
	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}
	public String getTo_province() {
		return to_province;
	}
	public void setTo_province(String to_province) {
		this.to_province = to_province;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getTo_state() {
		return to_state;
	}
	public void setTo_state(String to_state) {
		this.to_state = to_state;
	}
	public String getTo_street() {
		return to_street;
	}
	public void setTo_street(String to_street) {
		this.to_street = to_street;
	}
	public String getFrom_contacts() {
		return from_contacts;
	}
	public void setFrom_contacts(String from_contacts) {
		this.from_contacts = from_contacts;
	}
	public String getFrom_phone() {
		return from_phone;
	}
	public void setFrom_phone(String from_phone) {
		this.from_phone = from_phone;
	}
	public String getFrom_address() {
		return from_address;
	}
	public void setFrom_address(String from_address) {
		this.from_address = from_address;
	}
	public String getFrom_province() {
		return from_province;
	}
	public void setFrom_province(String from_province) {
		this.from_province = from_province;
	}
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getFrom_state() {
		return from_state;
	}
	public void setFrom_state(String from_state) {
		this.from_state = from_state;
	}
	public String getFrom_street() {
		return from_street;
	}
	public void setFrom_street(String from_street) {
		this.from_street = from_street;
	}
	public java.util.Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(java.util.Date order_time) {
		this.order_time = order_time;
	}
	public String getRoute_status() {
		return route_status;
	}
	public void setRoute_status(String route_status) {
		this.route_status = route_status;
	}
	public Integer getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(Integer total_qty) {
		this.total_qty = total_qty;
	}
	public java.math.BigDecimal getTotal_weight() {
		return total_weight;
	}
	public void setTotal_weight(java.math.BigDecimal total_weight) {
		this.total_weight = total_weight;
	}
	public java.math.BigDecimal getTotal_volumn() {
		return total_volumn;
	}
	public void setTotal_volumn(java.math.BigDecimal total_volumn) {
		this.total_volumn = total_volumn;
	}
	public java.math.BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(java.math.BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	public String getIsAutoBill() {
		return isAutoBill;
	}
	public void setIsAutoBill(String isAutoBill) {
		this.isAutoBill = isAutoBill;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getTo_phone2() {
		return to_phone2;
	}
	public void setTo_phone2(String to_phone2) {
		this.to_phone2 = to_phone2;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_source() {
		return customer_source;
	}
	public void setCustomer_source(String customer_source) {
		this.customer_source = customer_source;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCargo_type() {
		return cargo_type;
	}
	public void setCargo_type(String cargo_type) {
		this.cargo_type = cargo_type;
	}
	public String getTo_num() {
		return to_num;
	}
	public void setTo_num(String to_num) {
		this.to_num = to_num;
	}
	public String getFrom_num() {
		return from_num;
	}
	public void setFrom_num(String from_num) {
		this.from_num = from_num;
	}
	public void registerCustomEditor(Class<Date> class1, CustomDateEditor customDateEditor) {
		// TODO Auto-generated method stub
		
	}
	public String getFrom_contacts_like() {
		return from_contacts_like;
	}
	public void setFrom_contacts_like(String from_contacts_like) {
		this.from_contacts_like = from_contacts_like;
	}
	public String getTo_contacts_like() {
		return to_contacts_like;
	}
	public void setTo_contacts_like(String to_contacts_like) {
		this.to_contacts_like = to_contacts_like;
	}
	
	
}
