package com.lmis.jbasis.warehouse.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: JbasisWarehouse
 * @Description: TODO(仓库信息管理)
 * @author codeGenerator
 * @date 2018-04-17 09:52:14
 * 
 */
public class JbasisWarehouse extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "仓库代码")
	private String warehouseCode;
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
    @ApiModelProperty(value = "仓库名称")
	private String warehouseName;
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
    @ApiModelProperty(value = "省")
	private String province;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
    @ApiModelProperty(value = "市")
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
    @ApiModelProperty(value = "区")
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
    @ApiModelProperty(value = "街道")
	private String street;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
    @ApiModelProperty(value = "详细地址")
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
    @ApiModelProperty(value = "联系人")
	private String contact;
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
    @ApiModelProperty(value = "联系电话")
	private String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
    @ApiModelProperty(value = "仓库类型 0-自营仓 结算算在快递 1-外包仓 结算算在物流")
	private String warehouseType;
	public String getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	
    @ApiModelProperty(value = "有效性")
	private String validity;
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
    @ApiModelProperty(value = "0 nouse 1 use")
	private String woFlag;
	public String getWoFlag() {
		return woFlag;
	}
	public void setWoFlag(String woFlag) {
		this.woFlag = woFlag;
	}
	
    @ApiModelProperty(value = "省名称")
	private String provinceName;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
    @ApiModelProperty(value = "市名称")
	private String cityName;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
    @ApiModelProperty(value = "区名称")
	private String stateName;
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
    @ApiModelProperty(value = "街道名称")
	private String streetName;
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
}
