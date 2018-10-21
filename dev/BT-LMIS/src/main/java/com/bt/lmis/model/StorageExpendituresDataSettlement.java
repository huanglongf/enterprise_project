package com.bt.lmis.model;

/**
* @ClassName: StorageExpendituresDataSettlement
* @Description: TODO(StorageExpendituresDataSettlement)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class StorageExpendituresDataSettlement {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private java.math.BigDecimal storage_fee;			//仓储费用	private String remark;			//备注	private Integer data_id;			//原数据ID	private Integer operation_id;			//操作记录id	private String start_time;			//时间	private String store_name;			//店铺名称	private String warehouse_name;			//仓库名称	private String area_name;			//区域名称	private String item_type;			//商品类型	private Integer storage_type;			//存储类型(0:固定，1:面积，2:体积，3:托盘)	private java.math.BigDecimal storage_acreage;			//存储大小	private String acreage_unit;			//存储单位
	private String end_time;
	private String contract_name;
	private String contract_no;
	private String billing_cycle;
	private String batch;
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getBilling_cycle() {
		return billing_cycle;
	}
	public void setBilling_cycle(String billing_cycle) {
		this.billing_cycle = billing_cycle;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public java.math.BigDecimal getStorage_fee() {	    return this.storage_fee;	}	public void setStorage_fee(java.math.BigDecimal storage_fee) {	    this.storage_fee=storage_fee;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getData_id() {	    return this.data_id;	}	public void setData_id(Integer data_id) {	    this.data_id=data_id;	}	public Integer getOperation_id() {	    return this.operation_id;	}	public void setOperation_id(Integer operation_id) {	    this.operation_id=operation_id;	}	public String getStart_time() {	    return this.start_time;	}	public void setStart_time(String start_time) {	    this.start_time=start_time;	}	public String getStore_name() {	    return this.store_name;	}	public void setStore_name(String store_name) {	    this.store_name=store_name;	}	public String getWarehouse_name() {	    return this.warehouse_name;	}	public void setWarehouse_name(String warehouse_name) {	    this.warehouse_name=warehouse_name;	}	public String getArea_name() {	    return this.area_name;	}	public void setArea_name(String area_name) {	    this.area_name=area_name;	}	public String getItem_type() {	    return this.item_type;	}	public void setItem_type(String item_type) {	    this.item_type=item_type;	}	public Integer getStorage_type() {	    return this.storage_type;	}	public void setStorage_type(Integer storage_type) {	    this.storage_type=storage_type;	}	public java.math.BigDecimal getStorage_acreage() {	    return this.storage_acreage;	}	public void setStorage_acreage(java.math.BigDecimal storage_acreage) {	    this.storage_acreage=storage_acreage;	}	public String getAcreage_unit() {	    return this.acreage_unit;	}	public void setAcreage_unit(String acreage_unit) {	    this.acreage_unit=acreage_unit;	}
}
