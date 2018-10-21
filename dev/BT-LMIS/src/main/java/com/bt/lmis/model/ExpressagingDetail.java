package com.bt.lmis.model;

public class ExpressagingDetail {
	
	
	private String id;			//主键
	private java.util.Date create_time;			//创建时间
	private String create_user;			//创建人
	private java.util.Date update_time;			//更新时间
	private String update_user;			//更新人
	private Integer number;			//节点序号
	private String province_code;			//到达省
	private String city_code;			//到达市
	private String state_code;			//
	private String street_code;			//
	private java.math.BigDecimal efficiency;			//时效(H)
	private String efficiency_unit;			//时效单位(0:小时；1：天)
	private String warningtype_code;			//预警类型
	private Integer valibity;			//0:无效；1：有效
	private String pid;			//
	private String timeout_mode;
	public String getTimeout_mode() {
		return timeout_mode;
	}
	public void setTimeout_mode(String timeout_mode) {
		this.timeout_mode = timeout_mode;
	}
	public String getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(String orderCondition) {
		this.orderCondition = orderCondition;
	}
	private Integer dl_flag;			//删除标识
	private String orderCondition;
	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public java.util.Date getCreate_time() {
	    return this.create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
	    this.create_time=create_time;
	}
	public String getCreate_user() {
	    return this.create_user;
	}
	public void setCreate_user(String create_user) {
	    this.create_user=create_user;
	}
	public java.util.Date getUpdate_time() {
	    return this.update_time;
	}
	public void setUpdate_time(java.util.Date update_time) {
	    this.update_time=update_time;
	}
	public String getUpdate_user() {
	    return this.update_user;
	}
	public void setUpdate_user(String update_user) {
	    this.update_user=update_user;
	}
	public Integer getNumber() {
	    return this.number;
	}
	public void setNumber(Integer number) {
	    this.number=number;
	}
	public String getProvince_code() {
	    return this.province_code;
	}
	public void setProvince_code(String province_code) {
	    this.province_code=province_code;
	}
	public String getCity_code() {
	    return this.city_code;
	}
	public void setCity_code(String city_code) {
	    this.city_code=city_code;
	}
	public String getState_code() {
	    return this.state_code;
	}
	public void setState_code(String state_code) {
	    this.state_code=state_code;
	}
	public String getStreet_code() {
	    return this.street_code;
	}
	public void setStreet_code(String street_code) {
	    this.street_code=street_code;
	}
	public java.math.BigDecimal getEfficiency() {
	    return this.efficiency;
	}
	public void setEfficiency(java.math.BigDecimal efficiency) {
	    this.efficiency=efficiency;
	}
	public String getEfficiency_unit() {
	    return this.efficiency_unit;
	}
	public void setEfficiency_unit(String efficiency_unit) {
	    this.efficiency_unit=efficiency_unit;
	}
	public String getWarningtype_code() {
	    return this.warningtype_code;
	}
	public void setWarningtype_code(String warningtype_code) {
	    this.warningtype_code=warningtype_code;
	}
	public Integer getValibity() {
	    return this.valibity;
	}
	public void setValibity(Integer valibity) {
	    this.valibity=valibity;
	}
	public String getPid() {
	    return this.pid;
	}
	public void setPid(String pid) {
	    this.pid=pid;
	}
	public Integer getDl_flag() {
	    return this.dl_flag;
	}
	public void setDl_flag(Integer dl_flag) {
	    this.dl_flag=dl_flag;
	}
}
