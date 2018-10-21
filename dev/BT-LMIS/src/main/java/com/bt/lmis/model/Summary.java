package com.bt.lmis.model;

/**
* @ClassName: Summary
* @Description: TODO(Summary)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class Summary {
	
		private Integer id;			//	private String cycle;			//周期	private Integer type;			//类型(0客户 1店铺 2快递 3物流)	private String subject;			//主体	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//修改时间	private String update_user;			//修改人
	private String cb_id;
	private String contract_name;
	private String contract_no;
	private String billing_cycle;
	
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
	}	public String getSubject() {
		return subject;
	}
	public String getCb_id() {
		return cb_id;
	}
	public void setCb_id(String cb_id) {
		this.cb_id = cb_id;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getCycle() {	    return this.cycle;	}	public void setCycle(String cycle) {	    this.cycle=cycle;	}	public Integer getType() {	    return this.type;	}	public void setType(Integer type) {	    this.type=type;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}
}
