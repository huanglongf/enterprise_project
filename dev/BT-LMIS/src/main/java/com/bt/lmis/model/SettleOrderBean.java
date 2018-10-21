package com.bt.lmis.model;

import java.math.BigDecimal;
import java.sql.Date;

public class SettleOrderBean {
	private int id;
	private int bat_id;
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
	private Date transport_time;
	private BigDecimal collection_on_delivery;
	private BigDecimal order_amount;
	private String sku_number;
	private BigDecimal weight;
	private BigDecimal length;
	private BigDecimal width;
	private BigDecimal higth;
	private BigDecimal volumn;
	private String delivery_address;
	private String province;
	private String city;
	private String state;
	private int insurance_flag;
	private int cod_flag;
	private String shiptoname;
	private String phone;
	private String address;
	private String express_time;
	private String check_time;
	private String weight_time;
	private Date create_date;
	private String operation_time;
	private String pro_flag;
	private String pro_remark;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBat_id() {
		return bat_id;
	}

	public void setBat_id(int bat_id) {
		this.bat_id = bat_id;
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

	public Date getTransport_time() {
		return transport_time;
	}

	public void setTransport_time(Date transport_time) {
		this.transport_time = transport_time;
	}

	public BigDecimal getCollection_on_delivery() {
		return collection_on_delivery;
	}

	public void setCollection_on_delivery(BigDecimal collection_on_delivery) {
		this.collection_on_delivery = collection_on_delivery;
	}

	public BigDecimal getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(BigDecimal order_amount) {
		this.order_amount = order_amount;
	}

	public String getSku_number() {
		return sku_number;
	}

	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHigth() {
		return higth;
	}

	public void setHigth(BigDecimal higth) {
		this.higth = higth;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
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

	public int getInsurance_flag() {
		return insurance_flag;
	}

	public void setInsurance_flag(int insurance_flag) {
		this.insurance_flag = insurance_flag;
	}

	public int getCod_flag() {
		return cod_flag;
	}

	public void setCod_flag(int cod_flag) {
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

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
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
}
