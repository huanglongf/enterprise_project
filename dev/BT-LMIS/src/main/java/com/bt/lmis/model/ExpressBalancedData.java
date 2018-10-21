package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title:ExpressBalancedData
 * @Description: TODO(快递结算明细数据)
 * @author Ian.Huang 
 * @date 2016年7月19日下午4:23:34
 */
public class ExpressBalancedData {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String cost_center;
	private String store_code;
	private String store_name;
	private String warehouse;
	private String transport_code;
	private String transport_name;
	private String epistatic_order;
	private String delivery_order;
	private String order_type;
	private String express_number;
	private String transport_direction;
	private String itemtype_code;
	private String itemtype_name;
	private Date transport_time;
	private Date platform_order_time;
	private Date platform_pay_time;
	private BigDecimal collection_on_delivery;
	private BigDecimal order_amount;
	private String sku_number;
	private int qty;
	private BigDecimal weight;
	private BigDecimal account_weight;
	private BigDecimal length;
	private BigDecimal width;
	private BigDecimal higth;
	private BigDecimal volumn;
	private String delivery_address;
	private String province;
	private String city;
	private String state;
	private String street;
	private Boolean insurance_flag;
	private Boolean cod_flag;
	private String balance_subject;
	private BigDecimal jp_num;
	private BigDecimal volumn_weight;
	private BigDecimal volumn_account_weight;
	private BigDecimal jf_weight;
	private BigDecimal charged_weight;
	private BigDecimal first_weight;
	private BigDecimal first_weight_price;
	private BigDecimal added_weight;
	private BigDecimal added_weight_price;
	private BigDecimal charge_weight;
	private BigDecimal standard_freight;
	private BigDecimal discount;
	private BigDecimal afterdiscount_freight;
	private BigDecimal insurance_fee;
	private BigDecimal cod;
	private BigDecimal delegated_pickup;
	private BigDecimal package_price;
	private BigDecimal extend_prop1;
	private BigDecimal extend_prop2;
	private BigDecimal total_fee;
	private String remark;
	private int data_id;
	private int contract_id;
	private int dFlag;
	private String park_code;
	private String park_name;
	private String park_cost_center;
	
	private String province_origin;				//始发地省
	private String city_origin;					//始发地市
	private String state_origin;				//始发地区
	
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
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
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
	public String getEpistatic_order() {
		return epistatic_order;
	}
	public void setEpistatic_order(String epistatic_order) {
		this.epistatic_order = epistatic_order;
	}
	public String getDelivery_order() {
		return delivery_order;
	}
	public void setDelivery_order(String delivery_order) {
		this.delivery_order = delivery_order;
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getAccount_weight() {
		return account_weight;
	}
	public void setAccount_weight(BigDecimal account_weight) {
		this.account_weight = account_weight;
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Boolean getInsurance_flag() {
		return insurance_flag;
	}
	public void setInsurance_flag(Boolean insurance_flag) {
		this.insurance_flag = insurance_flag;
	}
	public Boolean getCod_flag() {
		return cod_flag;
	}
	public void setCod_flag(Boolean cod_flag) {
		this.cod_flag = cod_flag;
	}
	public String getBalance_subject() {
		return balance_subject;
	}
	public void setBalance_subject(String balance_subject) {
		this.balance_subject = balance_subject;
	}
	public BigDecimal getJp_num() {
		return jp_num;
	}
	public void setJp_num(BigDecimal jp_num) {
		this.jp_num = jp_num;
	}
	public BigDecimal getVolumn_weight() {
		return volumn_weight;
	}
	public void setVolumn_weight(BigDecimal volumn_weight) {
		this.volumn_weight = volumn_weight;
	}
	public BigDecimal getVolumn_account_weight() {
		return volumn_account_weight;
	}
	public void setVolumn_account_weight(BigDecimal volumn_account_weight) {
		this.volumn_account_weight = volumn_account_weight;
	}
	public BigDecimal getJf_weight() {
		return jf_weight;
	}
	public void setJf_weight(BigDecimal jf_weight) {
		this.jf_weight = jf_weight;
	}
	public BigDecimal getCharged_weight() {
		return charged_weight;
	}
	public void setCharged_weight(BigDecimal charged_weight) {
		this.charged_weight = charged_weight;
	}
	public BigDecimal getFirst_weight() {
		return first_weight;
	}
	public void setFirst_weight(BigDecimal first_weight) {
		this.first_weight = first_weight;
	}
	public BigDecimal getFirst_weight_price() {
		return first_weight_price;
	}
	public void setFirst_weight_price(BigDecimal first_weight_price) {
		this.first_weight_price = first_weight_price;
	}
	public BigDecimal getAdded_weight() {
		return added_weight;
	}
	public void setAdded_weight(BigDecimal added_weight) {
		this.added_weight = added_weight;
	}
	public BigDecimal getAdded_weight_price() {
		return added_weight_price;
	}
	public void setAdded_weight_price(BigDecimal added_weight_price) {
		this.added_weight_price = added_weight_price;
	}
	public BigDecimal getCharge_weight() {
		return charge_weight;
	}
	public void setCharge_weight(BigDecimal charge_weight) {
		this.charge_weight = charge_weight;
	}
	public BigDecimal getStandard_freight() {
		return standard_freight;
	}
	public void setStandard_freight(BigDecimal standard_freight) {
		this.standard_freight = standard_freight;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getAfterdiscount_freight() {
		return afterdiscount_freight;
	}
	public void setAfterdiscount_freight(BigDecimal afterdiscount_freight) {
		this.afterdiscount_freight = afterdiscount_freight;
	}
	public BigDecimal getInsurance_fee() {
		return insurance_fee;
	}
	public void setInsurance_fee(BigDecimal insurance_fee) {
		this.insurance_fee = insurance_fee;
	}
	public BigDecimal getCod() {
		return cod;
	}
	public void setCod(BigDecimal cod) {
		this.cod = cod;
	}
	public BigDecimal getDelegated_pickup() {
		return delegated_pickup;
	}
	public void setDelegated_pickup(BigDecimal delegated_pickup) {
		this.delegated_pickup = delegated_pickup;
	}
	public BigDecimal getPackage_price() {
		return package_price;
	}
	public void setPackage_price(BigDecimal package_price) {
		this.package_price = package_price;
	}
	public BigDecimal getExtend_prop1() {
		return extend_prop1;
	}
	public void setExtend_prop1(BigDecimal extend_prop1) {
		this.extend_prop1 = extend_prop1;
	}
	public BigDecimal getExtend_prop2() {
		return extend_prop2;
	}
	public void setExtend_prop2(BigDecimal extend_prop2) {
		this.extend_prop2 = extend_prop2;
	}
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getData_id() {
		return data_id;
	}
	public void setData_id(int data_id) {
		this.data_id = data_id;
	}
	public int getContract_id() {
		return contract_id;
	}
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}
	public int getdFlag() {
		return dFlag;
	}
	public void setdFlag(int dFlag) {
		this.dFlag = dFlag;
	}
	public Date getPlatform_order_time() {
		return platform_order_time;
	}
	public void setPlatform_order_time(Date platform_order_time) {
		this.platform_order_time = platform_order_time;
	}
	public Date getPlatform_pay_time() {
		return platform_pay_time;
	}
	public void setPlatform_pay_time(Date platform_pay_time) {
		this.platform_pay_time = platform_pay_time;
	}
	public String getPark_code() {
		return park_code;
	}
	public void setPark_code(String park_code) {
		this.park_code = park_code;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getPark_cost_center() {
		return park_cost_center;
	}
	public void setPark_cost_center(String park_cost_center) {
		this.park_cost_center = park_cost_center;
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
