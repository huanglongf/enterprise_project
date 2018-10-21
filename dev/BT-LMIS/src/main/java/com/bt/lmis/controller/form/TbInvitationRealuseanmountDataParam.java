package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

public class TbInvitationRealuseanmountDataParam extends QueryParameter{
	    private int id;
	    private int bat_id;
	    private  java.util.Date ob_time;
	    private  String create_time;
	    private String create_user;
	    private  java.util.Date update_time;
	    private String update_user;
	    private String store_code;
	    private String store_name;
	    private String cost_center;
	    private String use_time;
	    private String sku_code;
	    private String sku_name;
	    private java.math.BigDecimal use_amount;
	    private String use_amountunit;
	    private int settle_flag;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getBat_id() {
			return bat_id;
		}
		public void setBat_id(int bat_id) {
			this.bat_id = bat_id;
		}
		public java.util.Date getOb_time() {
			return ob_time;
		}
		public void setOb_time(java.util.Date ob_time) {
			this.ob_time = ob_time;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
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
		public String getStore_code() {
			return store_code;
		}
		public void setStore_code(String store_code) {
			this.store_code = store_code;
		}
		public String getStore_name() {
			return store_name;
		}
		public void setStore_name(String store_name) {
			this.store_name = store_name;
		}
		public String getCost_center() {
			return cost_center;
		}
		public void setCost_center(String cost_center) {
			this.cost_center = cost_center;
		}
		public String getUse_time() {
			return use_time;
		}
		public void setUse_time(String use_time) {
			this.use_time = use_time;
		}
		public String getSku_code() {
			return sku_code;
		}
		public void setSku_code(String sku_code) {
			this.sku_code = sku_code;
		}
		public String getSku_name() {
			return sku_name;
		}
		public void setSku_name(String sku_name) {
			this.sku_name = sku_name;
		}
		public java.math.BigDecimal getUse_amount() {
			return use_amount;
		}
		public void setUse_amount(java.math.BigDecimal use_amount) {
			this.use_amount = use_amount;
		}
		public String getUse_amountunit() {
			return use_amountunit;
		}
		public void setUse_amountunit(String use_amountunit) {
			this.use_amountunit = use_amountunit;
		}
		public int getSettle_flag() {
			return settle_flag;
		}
		public void setSettle_flag(int settle_flag) {
			this.settle_flag = settle_flag;
		}
}
