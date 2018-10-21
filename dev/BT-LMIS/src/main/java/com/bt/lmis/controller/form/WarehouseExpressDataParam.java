package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

public class WarehouseExpressDataParam extends QueryParameter {
	private int id;
	private int bat_id;
	private String create_time;
	private String create_user;
	private java.util.Date  update_time;
	private String update_user;
	private String cost_center;
	private String store_code;
	private String store_name;
	private String warehouse;
	private String transport_code;
	private String transport_name;
	private String delivery_order;
	private String epistatic_order;
	private String order_type;
	private java.math.BigDecimal express_number;
	private String transport_direction;
	private String itemtype_code;
	private String itemtype_name;
	private java.util.Date transport_time;
	private java.math.BigDecimal collection_on_delivery;
	private java.math.BigDecimal order_amount;
	private String sku_number;
	private java.math.BigDecimal weight;
	private java.math.BigDecimal length;
	private java.math.BigDecimal width;
	private java.math.BigDecimal higth;
	private java.math.BigDecimal volumn;
	private String delivery_address;
	private String province;
	private String city;
	private String state;
	private String street;
	private int insurance_flag;
	private int cod_flag;
	private String balance_subject;
	private int settle_flag;
	private int settle_logistics_flag;
	private int settle_client_flag;
	private int settle_store_flag;
	private int packing_charge_flag;
	private int dFlag;
	private int operating_flag;
	private int qty;
	private String hcf_settle_flag;
	private int ffer_id;
	
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
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
	public String getCost_center() {
		return cost_center;
	}
	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
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
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
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
	public java.math.BigDecimal getExpress_number() {
		return express_number;
	}
	public void setExpress_number(java.math.BigDecimal express_number) {
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
	public java.util.Date getTransport_time() {
		return transport_time;
	}
	public void setTransport_time(java.util.Date transport_time) {
		this.transport_time = transport_time;
	}
	public java.math.BigDecimal getCollection_on_delivery() {
		return collection_on_delivery;
	}
	public void setCollection_on_delivery(java.math.BigDecimal collection_on_delivery) {
		this.collection_on_delivery = collection_on_delivery;
	}
	public java.math.BigDecimal getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(java.math.BigDecimal order_amount) {
		this.order_amount = order_amount;
	}
	public String getSku_number() {
		return sku_number;
	}
	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}
	public java.math.BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(java.math.BigDecimal weight) {
		this.weight = weight;
	}
	public java.math.BigDecimal getLength() {
		return length;
	}
	public void setLength(java.math.BigDecimal length) {
		this.length = length;
	}
	public java.math.BigDecimal getWidth() {
		return width;
	}
	public void setWidth(java.math.BigDecimal width) {
		this.width = width;
	}
	public java.math.BigDecimal getHigth() {
		return higth;
	}
	public void setHigth(java.math.BigDecimal higth) {
		this.higth = higth;
	}
	public java.math.BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(java.math.BigDecimal volumn) {
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public String getBalance_subject() {
		return balance_subject;
	}
	public void setBalance_subject(String balance_subject) {
		this.balance_subject = balance_subject;
	}
	public int getSettle_flag() {
		return settle_flag;
	}
	public void setSettle_flag(int settle_flag) {
		this.settle_flag = settle_flag;
	}
	public int getSettle_logistics_flag() {
		return settle_logistics_flag;
	}
	public void setSettle_logistics_flag(int settle_logistics_flag) {
		this.settle_logistics_flag = settle_logistics_flag;
	}
	public int getSettle_client_flag() {
		return settle_client_flag;
	}
	public void setSettle_client_flag(int settle_client_flag) {
		this.settle_client_flag = settle_client_flag;
	}
	public int getSettle_store_flag() {
		return settle_store_flag;
	}
	public void setSettle_store_flag(int settle_store_flag) {
		this.settle_store_flag = settle_store_flag;
	}
	public int getPacking_charge_flag() {
		return packing_charge_flag;
	}
	public void setPacking_charge_flag(int packing_charge_flag) {
		this.packing_charge_flag = packing_charge_flag;
	}
	public int getdFlag() {
		return dFlag;
	}
	public void setdFlag(int dFlag) {
		this.dFlag = dFlag;
	}
	public int getOperating_flag() {
		return operating_flag;
	}
	public void setOperating_flag(int operating_flag) {
		this.operating_flag = operating_flag;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getHcf_settle_flag() {
		return hcf_settle_flag;
	}
	public void setHcf_settle_flag(String hcf_settle_flag) {
		this.hcf_settle_flag = hcf_settle_flag;
	}
	public int getFfer_id() {
		return ffer_id;
	}
	public void setFfer_id(int ffer_id) {
		this.ffer_id = ffer_id;
	}

}
