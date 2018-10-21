package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title:ExpressContractConfig
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年6月29日下午7:18:24
 */
public class ExpressContractConfig {
	private Integer id;
	private int con_id;
	private String belong_to;
	private int weight_method;
	private BigDecimal percent;
	private Integer percent_uom;
	private Boolean waybill_discount;
	private Boolean total_freight_discount;
	private Boolean insurance;
	private Boolean cod;
	private Boolean delegated_pickup;
	private Boolean dFlag;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public String getBelong_to() {
		return belong_to;
	}
	public void setBelong_to(String belong_to) {
		this.belong_to = belong_to;
	}
	public int getWeight_method() {
		return weight_method;
	}
	public void setWeight_method(int weight_method) {
		this.weight_method = weight_method;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getPercent_uom() {
		return percent_uom;
	}
	public void setPercent_uom(Integer percent_uom) {
		this.percent_uom = percent_uom;
	}
	public Boolean getWaybill_discount() {
		return waybill_discount;
	}
	public void setWaybill_discount(Boolean waybill_discount) {
		this.waybill_discount = waybill_discount;
	}
	public Boolean getTotal_freight_discount() {
		return total_freight_discount;
	}
	public void setTotal_freight_discount(Boolean total_freight_discount) {
		this.total_freight_discount = total_freight_discount;
	}
	public Boolean getInsurance() {
		return insurance;
	}
	public void setInsurance(Boolean insurance) {
		this.insurance = insurance;
	}
	public Boolean getCod() {
		return cod;
	}
	public void setCod(Boolean cod) {
		this.cod = cod;
	}
	public Boolean getDelegated_pickup() {
		return delegated_pickup;
	}
	public void setDelegated_pickup(Boolean delegated_pickup) {
		this.delegated_pickup = delegated_pickup;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
