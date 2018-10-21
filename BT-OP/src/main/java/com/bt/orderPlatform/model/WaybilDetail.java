package com.bt.orderPlatform.model;

import java.math.BigDecimal;

/**
* @ClassName: WaybilDetail
* @Description: TODO(WaybilDetail)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class WaybilDetail {
	
		private String id;			//	private java.util.Date create_time;			//	private String create_user;			//	private java.util.Date update_time;			//	private String update_user;			//	private String status;			//	private String order_id;			//订单号	private String sku_code;			//sku编码	private String sku_name;			//sku编码	private Integer qty;			//数量	private BigDecimal weight;			//毛重	private BigDecimal volumn;			//体积	private BigDecimal amount;			//金额
	private String serial_number;			//金额
	private String describe;			//描述
		public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getId() {
			    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public String getSku_code() {	    return this.sku_code;	}	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setSku_code(String sku_code) {	    this.sku_code=sku_code;	}	public String getSku_name() {	    return this.sku_name;	}	public void setSku_name(String sku_name) {	    this.sku_name=sku_name;	}	public Integer getQty() {	    return this.qty;	}	public void setQty(Integer qty) {	    this.qty=qty;	}	public java.math.BigDecimal getVolumn() {	    return this.volumn;	}	public void setVolumn(java.math.BigDecimal volumn) {	    this.volumn=volumn;	}	public java.math.BigDecimal getAmount() {	    return this.amount;	}	public void setAmount(java.math.BigDecimal amount) {	    this.amount=amount;	}
}
