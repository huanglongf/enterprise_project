package com.bt.orderPlatform.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年5月28日 下午4:21:51  
*/
public class WaybillMasterDetail extends Jurisdiction{

	@XmlTransient
	private String id;							//
	@XmlAttribute(name = "orderid")
	private String order_id;    				//订单号
	private java.util.Date create_time;			//系统创建时间
	private String create_user;					//创建人
	private java.util.Date update_time;			//系统更新时间
	private String update_user;					//修改人
	@XmlAttribute(name = "mailno")
	private String waybill;						//运单号
	@XmlAttribute(name = "j_company")
	private String from_orgnization;			//发货机构
	@XmlAttribute(name = "d_company")
	private String to_orgnization;				//收货机构
	private String status;						//状态代码 初始值为0
	@XmlAttribute(name = "express_type")
	private String producttype_code;			//业务类型代码
	@XmlTransient
	private String producttype_name;			//业务类型名称
	@XmlAttribute(name = "d_mobile")
	private String to_phone;					//收货人电话
	@XmlAttribute(name = "d_contact")
	private String to_contacts;					//收货联系人
	@XmlAttribute(name = "d_address")
	private String to_address;					//收货地址
	@XmlAttribute(name = "d_province")
	private String to_province;					//收货地省
	@XmlAttribute(name = "d_city")
	private String to_city;						//收货地市
	@XmlAttribute(name = "d_county")
	private String to_state;					//收货地区
	private String to_street;					//收货地街道
	@XmlAttribute(name = "j_contact")
	private String from_contacts;				//发货联系人
	@XmlAttribute(name = "j_mobile")		
	private String from_phone;					//发货人电话
	@XmlAttribute(name = "j_address")
	private String from_address;				//发货地址
	@XmlAttribute(name = "j_province")
	private String from_province;				//发货地省
	@XmlAttribute(name = "j_city")
	private String from_city;					//发货地市
	@XmlAttribute(name = "j_county")
	private String from_state;					//发货地区
	@XmlTransient
	private String from_street;					//发货地街道
	private java.util.Date order_time;			//下单日期
	private String route_status;				//路由状态
	@XmlAttribute(name = "parcel_quantity")
	private Integer total_qty;					//总件数，对应订单号有几个
	@XmlAttribute(name = "cargo_total_weight")
	private java.math.BigDecimal total_weight;	//总毛重(kg)
	@XmlAttribute(name = "volume")
	private java.math.BigDecimal total_volumn;	//总体积(立方厘米)
	@XmlAttribute(name = "declared_value")
	private java.math.BigDecimal total_amount;	//总费用
	
	private String isAutoBill;
	private String payment;
	private String expressCode;
	private String express_name;
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
	
	
	private String d_id;			//
	private String sku_code;			//sku编码
	private String sku_name;			//sku编码
	private Integer qty;			//数量
	private BigDecimal weight;			//毛重
	private BigDecimal volumn;			//体积
	private BigDecimal amount;			//金额
	private String serial_number;			//金额
	private String describe;			//描述
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
	private String print_code;	
	private String cost_center;	
	private String pay_path;	
	private  String store_code;   //店铺code
	private int ordinal; //同一批次序号

    public int getOrdinal(){
        return ordinal;
    }
    
    public void setOrdinal(int ordinal){
        this.ordinal = ordinal;
    }
    public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public String getCost_center() {
		return cost_center;
	}
	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
	}
	public String getPay_path() {
		return pay_path;
	}
	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getPrint_code() {
		return print_code;
	}
	public void setPrint_code(String print_code) {
		this.print_code = print_code;
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
	public String getFrom_orgnization_code() {
		return from_orgnization_code;
	}
	public void setFrom_orgnization_code(String from_orgnization_code) {
		this.from_orgnization_code = from_orgnization_code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getCustomer_number() {
		return customer_number;
	}
	public void setCustomer_number(String customer_number) {
		this.customer_number = customer_number;
	}
	public String getD_id() {
		return d_id;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public String getSku_code() {
		return sku_code;
	}
	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	
	public WaybillMasterDetail(){
		
	}
	
	public  WaybillMasterDetail(String[] row) {
		this.customer_number = row[0];
		this.express_name = row[1];
		this.producttype_name = row[2];
		this.payment = row[3];
		this.memo = row[4];
		this.to_orgnization=row[5];
		this.to_province = row[6];
		this.to_city = row[7];
		this.to_state = row[8];
		this.to_street = row[9];
		this.to_contacts = row[10];
		this.to_phone = row[11];
		this.to_address = row[12];
		this.from_province = row[13];
		this.from_city = row[14];
		this.from_state = row[15];
		this.from_street = row[16];
		this.from_contacts = row[17];
		this.from_phone = row[18];
		this.from_address = row[19];
		this.sku_code = row[20];
		this.sku_name = row[21];
		this.qty = Integer.parseInt(row[22]);
		this.weight = new BigDecimal(Integer.parseInt(row[23]));
		this.volumn = new BigDecimal(Integer.parseInt(row[24]));
		this.amount = new BigDecimal(Integer.parseInt(row[25]));
	}
}
