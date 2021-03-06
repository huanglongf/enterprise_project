package com.bt.lmis.model;

/** 
* @ClassName: WarehouseFeeRD 
* @Description: TODO(仓库原始数据) 
* @author Yuriy.Jiang
* @date 2016年7月17日 下午3:14:04 
*  
*/
public class WarehouseFeeRD {

	private Integer id;			//主键
	private java.util.Date create_time;			//创建时间
	private String create_user;			//创建人
	private java.util.Date update_time;			//更新时间
	private String update_user;			//更新人
	private Integer warehouse_type;			//大类(0:自营仓；1：外包仓)
	private java.util.Date start_time;			//时间
	private Integer store_id;			//
	private String store_name;			//店铺名称
	private Integer is_closed;			//是否关店(0:开店；1：关店)
	private String trade;			//行业
	private String warehouse_code;			//仓库代码
	private String warehouse_name;			//仓库名称
	private String system_warehouse;			//系统仓
	private String whcost_center;			//仓+成本中心
	private String cost_code;			//成本中心代码
	private String cost_name;			//
	private String area_code;			//区域代码
	private String area_name;			//区域名称
	private String item_type;			//商品类型
	private Integer storage_type;			//存储类型(0:托盘，1：面积，2：体积)
	private java.math.BigDecimal storage_acreage;			//存储大小
	private String acreage_unit;			//存储单位
	private Integer settle_flag;			//结算标识(0:未结算；1：已结算)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public java.util.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public java.util.Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(java.util.Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Integer getWarehouse_type() {
		return warehouse_type;
	}
	public void setWarehouse_type(Integer warehouse_type) {
		this.warehouse_type = warehouse_type;
	}
	public java.util.Date getStart_time() {
		return start_time;
	}
	public void setStart_time(java.util.Date start_time) {
		this.start_time = start_time;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public Integer getIs_closed() {
		return is_closed;
	}
	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
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
	public String getSystem_warehouse() {
		return system_warehouse;
	}
	public void setSystem_warehouse(String system_warehouse) {
		this.system_warehouse = system_warehouse;
	}
	public String getWhcost_center() {
		return whcost_center;
	}
	public void setWhcost_center(String whcost_center) {
		this.whcost_center = whcost_center;
	}
	public String getCost_code() {
		return cost_code;
	}
	public void setCost_code(String cost_code) {
		this.cost_code = cost_code;
	}
	public String getCost_name() {
		return cost_name;
	}
	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public Integer getStorage_type() {
		return storage_type;
	}
	public void setStorage_type(Integer storage_type) {
		this.storage_type = storage_type;
	}
	public java.math.BigDecimal getStorage_acreage() {
		return storage_acreage;
	}
	public void setStorage_acreage(java.math.BigDecimal storage_acreage) {
		this.storage_acreage = storage_acreage;
	}
	public String getAcreage_unit() {
		return acreage_unit;
	}
	public void setAcreage_unit(String acreage_unit) {
		this.acreage_unit = acreage_unit;
	}
	public Integer getSettle_flag() {
		return settle_flag;
	}
	public void setSettle_flag(Integer settle_flag) {
		this.settle_flag = settle_flag;
	}
	
}
