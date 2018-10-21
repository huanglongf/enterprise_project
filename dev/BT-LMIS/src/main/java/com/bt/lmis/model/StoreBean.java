package com.bt.lmis.model;


/** 
* @ClassName: Employee 
* @Description: TODO(员工) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:55 
*  
*/
public class StoreBean {
	private String id;
	private String create_by;
	private String create_time;
	private String update_by;
	private String update_time;
	private String group;
	private String selfwarehouse;
	private String outsourcedwarehouse;
	private String store;
	private String store_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSelfwarehouse() {
		return selfwarehouse;
	}
	public void setSelfwarehouse(String selfwarehouse) {
		this.selfwarehouse = selfwarehouse;
	}
	public String getOutsourcedwarehouse() {
		return outsourcedwarehouse;
	}
	public void setOutsourcedwarehouse(String outsourcedwarehouse) {
		this.outsourcedwarehouse = outsourcedwarehouse;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	
}
