package com.bt.lmis.model;

/**
* @ClassName: PerationfeeData
* @Description: TODO(PerationfeeData)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class PerationfeeData {
	
	
	private Integer bat_id;
	private Integer sku_count;
    private String start_time;
    private String end_time;
    
    private String warehouse_type;
    private String warehouse_name;
    private String warehouse_code;
    private String platform_order;
    
    private String brand_docking_code;
    private String barcode;
    private String epistatic_order;
    
    private String park_code;
    private String park_name;
    private String park_cost_center;
    
    private Integer firstResult;
    private Integer maxResult;
    private java.util.Date settle_date;

	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getSku_count() {
		return sku_count;
	}
	public void setSku_count(Integer sku_count) {
		this.sku_count = sku_count;
	}
	public Integer getId() {
	public Integer getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	public Integer getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
	public Integer getBat_id() {
		return bat_id;
	}
	public void setBat_id(Integer bat_id) {
		this.bat_id = bat_id;
	}
	public String getWarehouse_type() {
		return warehouse_type;
	}
	public void setWarehouse_type(String warehouse_type) {
		this.warehouse_type = warehouse_type;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getWarehouse_code() {
		return warehouse_code;
	}
	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}
	public String getPlatform_order() {
		return platform_order;
	}
	public void setPlatform_order(String platform_order) {
		this.platform_order = platform_order;
	}
	public String getBrand_docking_code() {
		return brand_docking_code;
	}
	public void setBrand_docking_code(String brand_docking_code) {
		this.brand_docking_code = brand_docking_code;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getEpistatic_order() {
		return epistatic_order;
	}
	public void setEpistatic_order(String epistatic_order) {
		this.epistatic_order = epistatic_order;
	}
	public java.util.Date getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(java.util.Date settle_date) {
		this.settle_date = settle_date;
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
}