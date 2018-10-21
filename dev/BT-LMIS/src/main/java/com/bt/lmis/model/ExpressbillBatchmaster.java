package com.bt.lmis.model;

/**
* @ClassName: ExpressbillBatchmaster
* @Description: TODO(ExpressbillBatchmaster)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class ExpressbillBatchmaster {
	
		private Integer id;			//	private String master_id;			//	private String batch_id;			//	private Integer success_num;			//	private Integer fail_num;			//	private java.util.Date create_time;			//	private String create_user;			//	private Integer total_num;			//
	private String status;
	private String local_address;
	private String billingCycle;	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getMaster_id() {	    return this.master_id;	}	public void setMaster_id(String master_id) {	    this.master_id=master_id;	}	public String getBatch_id() {	    return this.batch_id;	}	public void setBatch_id(String batch_id) {	    this.batch_id=batch_id;	}	public Integer getSuccess_num() {	    return this.success_num;	}	public void setSuccess_num(Integer success_num) {	    this.success_num=success_num;	}	public Integer getFail_num() {	    return this.fail_num;	}	public void setFail_num(Integer fail_num) {	    this.fail_num=fail_num;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public Integer getTotal_num() {	    return this.total_num;	}	public void setTotal_num(Integer total_num) {	    this.total_num=total_num;	}
	public String getLocal_address() {
		return local_address;
	}
	public void setLocal_address(String local_address) {
		this.local_address = local_address;
	}
}
