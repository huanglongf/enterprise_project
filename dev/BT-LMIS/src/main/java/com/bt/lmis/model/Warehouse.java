package com.bt.lmis.model;

/**
* @ClassName: Warehouse
* @Description: TODO(Warehouse)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class Warehouse {
	private Integer id;			//主键
	private String province_name;			//省
	private String city_name;			//市
	private String state_name;			//区
	private String street_name;			//街道
	private String warehouse_type;
	private String need_balance;            //是否需要结算
	public String getWarehouse_type() {
		return warehouse_type;
	}
	public void setWarehouse_type(String warehouse_type) {
		this.warehouse_type = warehouse_type;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
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
	public String getNeed_balance() {
		return need_balance;
	}
	public void setNeed_balance(String need_balance) {
		this.need_balance = need_balance;
	}
	
}