package com.bt.lmis.model;

/**
* @ClassName: Warehouse
* @Description: TODO(Warehouse)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class Warehouse {
	private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String warehouse_code;			//仓库代码	private String warehouse_name;			//仓库名称	private Integer province;			//省	private Integer city;			//市	private Integer state;			//区	private Integer street;			//街道	private String address;			//详细地址	private String contact;			//联系人	private String phone;			//联系电话
	private String province_name;			//省
	private String city_name;			//市
	private String state_name;			//区
	private String street_name;			//街道
	private String warehouse_type;	private Integer validity;			//有效性
	private String need_balance;            //是否需要结算	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getWarehouse_code() {	    return this.warehouse_code;	}	public void setWarehouse_code(String warehouse_code) {	    this.warehouse_code=warehouse_code;	}	public String getWarehouse_name() {	    return this.warehouse_name;	}	public void setWarehouse_name(String warehouse_name) {	    this.warehouse_name=warehouse_name;	}	public Integer getProvince() {	    return this.province;	}	public void setProvince(Integer province) {	    this.province=province;	}	public Integer getCity() {	    return this.city;	}	public void setCity(Integer city) {	    this.city=city;	}	public Integer getState() {	    return this.state;	}	public void setState(Integer state) {	    this.state=state;	}	public Integer getStreet() {	    return this.street;	}	public void setStreet(Integer street) {	    this.street=street;	}	public String getAddress() {	    return this.address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getContact() {	    return this.contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getPhone() {	    return this.phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public Integer getValidity() {	    return this.validity;	}	public void setValidity(Integer validity) {	    this.validity=validity;	}
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
