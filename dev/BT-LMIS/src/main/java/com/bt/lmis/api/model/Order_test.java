package com.bt.lmis.api.model;

import java.util.List;

public class Order_test {

	private String bat_id;

	private String upper_id;

	private String store_code;

    private String store_name;

    private String warehouse_code;

    private String warehouse_name;

    private String transport_code;

    private String transport_name;

    private String delivery_order;

    private String epistatic_order;

    private String order_type;

    private String express_number;

    private String transport_direction;

    private String itemtype_code;

    private String itemtype_name;

    private String transport_time;

    private String collection_on_delivery;

    private String order_amount;

    private String sku_number;

    private Integer sku_qty;
    
    private Integer qty;

    private String weight;

    private String length;

    private String width;

    private String higth;

    private String volumn;

    private String delivery_address;
    
    private String province_origin;
    
    private String city_origin;
    
    private String state_origin;

    private String province;

    private String city;

    private String state;

    private String insurance_flag;

    private String cod_flag;

    private String shiptoname;

    private String phone;

    private String address;

    private String express_time;

    private String check_time;

    private String weight_time;

    private String platform_pay_time; // 平台付款时间

    private String platform_order_time; //平台订单时间

    private String k_flag; //子包裹标识（0:主包裹/1：子包裹）

    private String operation_time;
    
    private Integer is_transferring;
    
    private String is_complete;
    
    private String pro_flag;
    
    private String pro_remark;
    
    private String er_flag;
    
    private String er_remark;
    
    private String api_mark;

    private Integer platform_mark; //接口调用标识 3- 全国报价新增订单导入
	
	private Integer settle_flag;		//是否需结算收入费用 （0否，1是）

	private String error_array_remark;//错误备注
    
    private String ukey;

    private List<OrderDetail_test> order_details;

	public String getBat_id() {
		return bat_id;
	}

	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}

	public String getUpper_id() {
		return upper_id;
	}

	public void setUpper_id(String upper_id) {
		this.upper_id = upper_id;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getWarehouse_code() {
		return warehouse_code;
	}

	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public String getTransport_code() {
		return transport_code;
	}

	public void setTransport_code(String transport_code) {
		this.transport_code = transport_code;
	}

	public String getTransport_name() {
		return transport_name;
	}

	public void setTransport_name(String transport_name) {
		this.transport_name = transport_name;
	}

	public String getDelivery_order() {
		return delivery_order;
	}

	public void setDelivery_order(String delivery_order) {
		this.delivery_order = delivery_order;
	}

	public String getEpistatic_order() {
		return epistatic_order;
	}

	public void setEpistatic_order(String epistatic_order) {
		this.epistatic_order = epistatic_order;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getExpress_number() {
		return express_number;
	}

	public void setExpress_number(String express_number) {
		this.express_number = express_number;
	}

	public String getTransport_direction() {
		return transport_direction;
	}

	public void setTransport_direction(String transport_direction) {
		this.transport_direction = transport_direction;
	}

	public String getItemtype_code() {
		return itemtype_code;
	}

	public void setItemtype_code(String itemtype_code) {
		this.itemtype_code = itemtype_code;
	}

	public String getItemtype_name() {
		return itemtype_name;
	}

	public void setItemtype_name(String itemtype_name) {
		this.itemtype_name = itemtype_name;
	}

	public String getTransport_time() {
		return transport_time;
	}

	public void setTransport_time(String transport_time) {
		this.transport_time = transport_time;
	}

	public String getCollection_on_delivery() {
		return collection_on_delivery;
	}

	public void setCollection_on_delivery(String collection_on_delivery) {
		this.collection_on_delivery = collection_on_delivery;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getSku_number() {
		return sku_number;
	}

	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHigth() {
		return higth;
	}

	public void setHigth(String higth) {
		this.higth = higth;
	}

	public String getVolumn() {
		return volumn;
	}

	public void setVolumn(String volumn) {
		this.volumn = volumn;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInsurance_flag() {
		return insurance_flag;
	}

	public void setInsurance_flag(String insurance_flag) {
		this.insurance_flag = insurance_flag;
	}

	public String getCod_flag() {
		return cod_flag;
	}

	public void setCod_flag(String cod_flag) {
		this.cod_flag = cod_flag;
	}

	public String getShiptoname() {
		return shiptoname;
	}

	public void setShiptoname(String shiptoname) {
		this.shiptoname = shiptoname;
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

	public String getExpress_time() {
		return express_time;
	}

	public void setExpress_time(String express_time) {
		this.express_time = express_time;
	}

	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

	public String getWeight_time() {
		return weight_time;
	}

	public void setWeight_time(String weight_time) {
		this.weight_time = weight_time;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

	public int getSku_qty() {
		return sku_qty;
	}

	public void setSku_qty(int sku_qty) {
		this.sku_qty = sku_qty;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getK_flag() {
		return k_flag;
	}

	public void setK_flag(String k_flag) {
		this.k_flag = k_flag;
	}

	public String getPlatform_order_time() {
		return platform_order_time;
	}

	public void setPlatform_order_time(String platform_order_time) {
		this.platform_order_time = platform_order_time;
	}

	public String getPlatform_pay_time() {
		return platform_pay_time;
	}

	public void setPlatform_pay_time(String platform_pay_time) {
		this.platform_pay_time = platform_pay_time;
	}

	public List<OrderDetail_test> getOrder_details() {
		return order_details;
	}

	public void setOrder_details(List<OrderDetail_test> order_details) {
		this.order_details = order_details;
	}

	public Integer getPlatform_mark() {
		return platform_mark;
	}

	public void setPlatform_mark(Integer platform_mark) {
		this.platform_mark = platform_mark;
	}

	public String getError_array_remark() {
		return error_array_remark;
	}

	public void setError_array_remark(String error_array_remark) {
		this.error_array_remark = error_array_remark;
	}

	public Integer getSettle_flag() {
		return settle_flag;
	}

	public void setSettle_flag(Integer settle_flag) {
		this.settle_flag = settle_flag;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getIs_transferring() {
		return is_transferring;
	}

	public void setIs_transferring(Integer is_transferring) {
		this.is_transferring = is_transferring;
	}

	public String getIs_complete() {
		return is_complete;
	}

	public void setIs_complete(String is_complete) {
		this.is_complete = is_complete;
	}

	public String getPro_flag() {
		return pro_flag;
	}

	public void setPro_flag(String pro_flag) {
		this.pro_flag = pro_flag;
	}

	public String getPro_remark() {
		return pro_remark;
	}

	public void setPro_remark(String pro_remark) {
		this.pro_remark = pro_remark;
	}

	public String getEr_flag() {
		return er_flag;
	}

	public void setEr_flag(String er_flag) {
		this.er_flag = er_flag;
	}

	public String getEr_remark() {
		return er_remark;
	}

	public void setEr_remark(String er_remark) {
		this.er_remark = er_remark;
	}

	public String getApi_mark() {
		return api_mark;
	}

	public void setApi_mark(String api_mark) {
		this.api_mark = api_mark;
	}

	public void setSku_qty(Integer sku_qty) {
		this.sku_qty = sku_qty;
	}

	public String getProvince_origin() {
		return province_origin;
	}

	public void setProvince_origin(String province_origin) {
		this.province_origin = province_origin;
	}

	public String getCity_origin() {
		return city_origin;
	}

	public void setCity_origin(String city_origin) {
		this.city_origin = city_origin;
	}

	public String getState_origin() {
		return state_origin;
	}

	public void setState_origin(String state_origin) {
		this.state_origin = state_origin;
	}
}
