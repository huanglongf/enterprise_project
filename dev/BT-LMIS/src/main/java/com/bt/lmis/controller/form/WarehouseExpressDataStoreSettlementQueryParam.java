package com.bt.lmis.controller.form;
import java.math.BigDecimal;
import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class WarehouseExpressDataStoreSettlementQueryParam extends QueryParameter {
	
	
	private String time;
	private Integer insurance_flag;
	private String epistatic_order;
	private String collection_on_delivery;
	private String startTime1;
	private String endTime1;
	private String contractName;
	
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}
	public Integer getdFlag() {
		return dFlag;
	}
	public void setdFlag(Integer dFlag) {
		this.dFlag = dFlag;
	}
	public String getEpistatic_order() {
		return epistatic_order;
	}
	public void setEpistatic_order(String epistatic_order) {
		this.epistatic_order = epistatic_order;
	}
	public String getCollection_on_delivery() {
		return collection_on_delivery;
	}
	public void setCollection_on_delivery(String collection_on_delivery) {
		this.collection_on_delivery = collection_on_delivery;
	}
	public Integer getInsurance_flag() {
		return insurance_flag;
	}
	public void setInsurance_flag(Integer insurance_flag) {
		this.insurance_flag = insurance_flag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getId() {
	public WarehouseExpressDataStoreSettlementQueryParam(Integer id, Date create_time, String create_user,
			Date update_time, String update_user, String cost_center, String store_code, String store_name,
			String warehouse, String transport_direction, String transport_code, String transport_name,
			String itemtype_name, String itemtype_code, String delivery_order, String order_type, String express_number,
			Date transport_time, Date bus_date, BigDecimal cargo_value, BigDecimal order_amount, String sku_number,
			BigDecimal weight, BigDecimal length, BigDecimal width, BigDecimal higth, BigDecimal volumn,
			String delivery_address, String province, String city, String state, String street, String cod_flag,
			String balance_subject, BigDecimal s_weight, BigDecimal x_weight, BigDecimal charged_weight,
			BigDecimal standard_freight, BigDecimal discount, BigDecimal afterdiscount_freight,
			BigDecimal insurance_fee, BigDecimal cod, BigDecimal delegated_pickup, BigDecimal package_price,
			BigDecimal extend_prop1, BigDecimal extend_prop2, BigDecimal total_fee, String remark, Integer data_id,
			Integer contract_id, Integer dFlag, String time, Integer insurance_flag, String epistatic_order,
			String collection_on_delivery, String startTime1, String endTime1) {
		super();
		this.id = id;
		this.create_time = create_time;
		this.create_user = create_user;
		this.update_time = update_time;
		this.update_user = update_user;
		this.cost_center = cost_center;
		this.store_code = store_code;
		this.store_name = store_name;
		this.warehouse = warehouse;
		this.transport_direction = transport_direction;
		this.transport_code = transport_code;
		this.transport_name = transport_name;
		this.itemtype_name = itemtype_name;
		this.itemtype_code = itemtype_code;
		this.delivery_order = delivery_order;
		this.order_type = order_type;
		this.express_number = express_number;
		this.transport_time = transport_time;
		this.bus_date = bus_date;
		this.cargo_value = cargo_value;
		this.order_amount = order_amount;
		this.sku_number = sku_number;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.higth = higth;
		this.volumn = volumn;
		this.delivery_address = delivery_address;
		this.province = province;
		this.city = city;
		this.state = state;
		this.street = street;
		this.cod_flag = cod_flag;
		this.balance_subject = balance_subject;
		this.s_weight = s_weight;
		this.x_weight = x_weight;
		this.charged_weight = charged_weight;
		this.standard_freight = standard_freight;
		this.discount = discount;
		this.afterdiscount_freight = afterdiscount_freight;
		this.insurance_fee = insurance_fee;
		this.cod = cod;
		this.delegated_pickup = delegated_pickup;
		this.package_price = package_price;
		this.extend_prop1 = extend_prop1;
		this.extend_prop2 = extend_prop2;
		this.total_fee = total_fee;
		this.remark = remark;
		this.data_id = data_id;
		this.contract_id = contract_id;
		this.dFlag = dFlag;
		this.time = time;
		this.insurance_flag = insurance_flag;
		this.epistatic_order = epistatic_order;
		this.collection_on_delivery = collection_on_delivery;
		this.startTime1 = startTime1;
		this.endTime1 = endTime1;
	}
	
	public WarehouseExpressDataStoreSettlementQueryParam() {
		super();
	}
	@Override
	public String toString() {
		return "WarehouseExpressDataStoreSettlementQueryParam [id=" + id + ", create_time=" + create_time
				+ ", create_user=" + create_user + ", update_time=" + update_time + ", update_user=" + update_user
				+ ", cost_center=" + cost_center + ", store_code=" + store_code + ", store_name=" + store_name
				+ ", warehouse=" + warehouse + ", transport_direction=" + transport_direction + ", transport_code="
				+ transport_code + ", transport_name=" + transport_name + ", itemtype_name=" + itemtype_name
				+ ", itemtype_code=" + itemtype_code + ", delivery_order=" + delivery_order + ", order_type="
				+ order_type + ", express_number=" + express_number + ", transport_time=" + transport_time
				+ ", bus_date=" + bus_date + ", cargo_value=" + cargo_value + ", order_amount=" + order_amount
				+ ", sku_number=" + sku_number + ", weight=" + weight + ", length=" + length + ", width=" + width
				+ ", higth=" + higth + ", volumn=" + volumn + ", delivery_address=" + delivery_address + ", province="
				+ province + ", city=" + city + ", state=" + state + ", street=" + street + ", cod_flag=" + cod_flag
				+ ", balance_subject=" + balance_subject + ", s_weight=" + s_weight + ", x_weight=" + x_weight
				+ ", charged_weight=" + charged_weight + ", standard_freight=" + standard_freight + ", discount="
				+ discount + ", afterdiscount_freight=" + afterdiscount_freight + ", insurance_fee=" + insurance_fee
				+ ", cod=" + cod + ", delegated_pickup=" + delegated_pickup + ", package_price=" + package_price
				+ ", extend_prop1=" + extend_prop1 + ", extend_prop2=" + extend_prop2 + ", total_fee=" + total_fee
				+ ", remark=" + remark + ", data_id=" + data_id + ", contract_id=" + contract_id + ", dFlag=" + dFlag
				+ ", time=" + time + ", insurance_flag=" + insurance_flag + ", epistatic_order=" + epistatic_order
				+ ", collection_on_delivery=" + collection_on_delivery + ", startTime1=" + startTime1 + ", endTime1="
				+ endTime1 + "]";
	}
	
}