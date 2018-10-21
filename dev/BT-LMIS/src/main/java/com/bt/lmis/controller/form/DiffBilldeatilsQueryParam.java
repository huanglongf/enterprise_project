package com.bt.lmis.controller.form;
import java.io.Serializable;
import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class DiffBilldeatilsQueryParam extends QueryParameter implements Serializable    {
	
	
	 * 
	 */
	private static final long serialVersionUID = -8550388928851934984L;
	private Integer id;			//
	private String master_id;			//
    private String close_account;          //0未关账 1已关账
	private String account_id;			//
	private java.math.BigDecimal total_discount; //总运费折扣
	private String accountparam;
	private String bat_id;
	private String express_name;
	private String producttype_name;
	private String month_accountq;
	private String cal_batid;
	private String bf_flag;
	public Integer getMinId() {
		return minId;
	}
	public void setMinId(Integer minId) {
		this.minId = minId;
	}
	public Integer getMaxId() {
		return maxId;
	}
	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}
	private String detail_status;
	private Integer minId;
	private Integer maxId;
	public String getCal_batid() {
		return cal_batid;
	}
	public void setCal_batid(String cal_batid) {
		this.cal_batid = cal_batid;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getProducttype_name() {
		return producttype_name;
	}
	public void setProducttype_name(String producttype_name) {
		this.producttype_name = producttype_name;
	}
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getMonth_account() {
		return month_account;
	}
	public void setMonth_account(String month_account) {
		this.month_account = month_account;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public Integer getId() {
	public java.math.BigDecimal getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(java.math.BigDecimal total_discount) {
		this.total_discount = total_discount;
	}
	public String getAccountparam() {
		return accountparam;
	}
	public void setAccountparam(String accountparam) {
		this.accountparam = accountparam;
	}
	public String getClose_account() {
		return close_account;
	}
	public void setClose_account(String close_account) {
		this.close_account = close_account;
	}
	public String getMonth_accountq() {
		return month_accountq;
	}
	public void setMonth_accountq(String month_accountq) {
		this.month_accountq = month_accountq;
	}
	public String getBf_flag() {
		return bf_flag;
	}
	public void setBf_flag(String bf_flag) {
		this.bf_flag = bf_flag;
	}
	public String getDetail_status() {
		return detail_status;
	}
	public void setDetail_status(String detail_status) {
		this.detail_status = detail_status;
	}
}