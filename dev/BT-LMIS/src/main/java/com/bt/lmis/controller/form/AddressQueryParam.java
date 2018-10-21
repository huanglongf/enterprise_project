package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:AddressQueryParam
 * @Description: TODO(始发地目的地参数查询条件)
 * @author Ian.Huang 
 * @date 2016年12月16日上午10:34:24
 */
public class AddressQueryParam extends QueryParameter {
	// 合同ID（模糊匹配下拉列表）
	private Integer contract_id;
	// 物流商代码（模糊匹配下拉列表）
	private String carrier_code;
	// 产品类型代码（根据快递名称模糊匹配下拉列表）
	private String itemtype_code;
	// 始发地（模糊匹配下拉列表）
	private String origination;
	// 目的地省（模糊匹配下拉列表）
	private String province_destination;
	// 目的地市（模糊匹配下拉列表）
	private String city_destination;
	// 目的地区（模糊匹配下拉列表）
	private String district_destination;
	// 始发地省（模糊匹配下拉列表）
	private String province_origin;
	// 始发地市（模糊匹配下拉列表）
	private String city_origin;
	// 始发地区（模糊匹配下拉列表）
	private String state_origin;

	public Integer getContract_id() {
		return contract_id;
	}
	public void setContract_id(Integer contract_id) {
		this.contract_id = contract_id;
	}
	public String getCarrier_code() {
		return carrier_code;
	}
	public void setCarrier_code(String carrier_code) {
		this.carrier_code = carrier_code;
	}
	public String getItemtype_code() {
		return itemtype_code;
	}
	public void setItemtype_code(String itemtype_code) {
		this.itemtype_code = itemtype_code;
	}
	public String getOrigination() {
		return origination;
	}
	public void setOrigination(String origination) {
		this.origination = origination;
	}
	public String getProvince_destination() {
		return province_destination;
	}
	public void setProvince_destination(String province_destination) {
		this.province_destination = province_destination;
	}
	public String getCity_destination() {
		return city_destination;
	}
	public void setCity_destination(String city_destination) {
		this.city_destination = city_destination;
	}
	public String getDistrict_destination() {
		return district_destination;
	}
	public void setDistrict_destination(String district_destination) {
		this.district_destination = district_destination;
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
