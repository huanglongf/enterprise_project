package com.bt.lmis.model;


public class ErrorAddress {
	private String id;
	private String upper_id;
	private String bat_id;
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
	private String sku_qty;
	private String qty;
	private String weight;
	private String length;
	private String width;
	private String higth;
	private String volumn;
	private String delivery_address;
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
	private String create_date;
	private String operation_time;
	private String is_transferring;
	private String is_complete;
	private String pro_flag;
	private String pro_remark;
    private String init_count;
    private String success_count;
    private String error_count;
    private String trans_flag;
    private String create_by;
	 
	 public ErrorAddress(String[] row,String bath_id,Employee user) {
		 this.id=row[0];
		 this.upper_id=row[1];
		 this.bat_id=bath_id;
		 this.store_code=row[2];
		 this.store_name=row[3];
		 this.warehouse_code=row[4];
		 this.warehouse_name=row[5];
		 this.transport_code=row[6];
		 this.transport_name=row[7];
		 this.delivery_order=row[8];
		 this.epistatic_order=row[9];
		 this. order_type=row[10];
		 this.express_number=row[11];
		 this. transport_direction=row[12];
		 this. itemtype_code=row[13];
		 this.itemtype_name=row[14];
		 this.transport_time=row[15];
		 this.collection_on_delivery=row[16];
		 this.order_amount=row[17];
		 this.sku_number=row[18];
		 this.sku_qty=row[19];
		 this.weight=row[20];
		 this.length=row[21];
		 this.width=row[22];
		 this.higth=row[23];
		 this.volumn=row[24];
		 this.delivery_address=row[25];
		 this.province=row[26];
		 this.city=row[27];
		 this.state=row[28];
		 this. insurance_flag=row[29];
		 this.cod_flag=row[30];
		 this.shiptoname=row[31];
		 this.phone=row[32];
		 this.address=row[33];
		 this.express_time=row[34];
		 this.check_time=row[35];
		 this.weight_time=row[36];
		 this.create_by=user.getName();
		}
	 
	 
	public ErrorAddress() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUpper_id() {
		return upper_id;
	}


	public void setUpper_id(String upper_id) {
		this.upper_id = upper_id;
	}


	public String getBat_id() {
		return bat_id;
	}


	public void setBat_id(String bat_id) {
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


	public String getSku_qty() {
		return sku_qty;
	}


	public void setSku_qty(String sku_qty) {
		this.sku_qty = sku_qty;
	}


	public String getQty() {
		return qty;
	}


	public void setQty(String qty) {
		this.qty = qty;
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


	public String getCreate_date() {
		return create_date;
	}


	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}


	public String getOperation_time() {
		return operation_time;
	}


	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}


	public String getIs_transferring() {
		return is_transferring;
	}


	public void setIs_transferring(String is_transferring) {
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


	public String getInit_count() {
		return init_count;
	}


	public void setInit_count(String init_count) {
		this.init_count = init_count;
	}


	public String getSuccess_count() {
		return success_count;
	}


	public void setSuccess_count(String success_count) {
		this.success_count = success_count;
	}


	public String getError_count() {
		return error_count;
	}


	public void setError_count(String error_count) {
		this.error_count = error_count;
	}

	public String getTrans_flag() {
		return trans_flag;
	}

	public void setTrans_flag(String trans_flag) {
		this.trans_flag = trans_flag;
	}


	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	
	
}
