package com.bt.lmis.model;

/**
* @ClassName: ExpressbillMaster
* @Description: TODO(ExpressbillMaster)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class ExpressbillMaster {
	
		private Integer id;			//	private java.util.Date create_time;			//	private String create_user;			//	private java.util.Date update_time;			//	private String update_user;			//	private String bill_name;			//主账单名称	private Integer bill_num;			//账单条数	private String status;			//0 初始状态 1 处理中（导入，匹配）	private String express_code;			//	private Integer con_id;			//合同id	private String billingCycle;			//账单周期	private String remarks;			//备注
	private String express_name;
	private String contract_name;
	private String bf_flag;        //备份标识作业
	private String bf_reason;      //备份标识作业失败原因
		public String getBf_flag() {
		return bf_flag;
	}
	public void setBf_flag(String bf_flag) {
		this.bf_flag = bf_flag;
	}
	public String getBf_reason() {
		return bf_reason;
	}
	public void setBf_reason(String bf_reason) {
		this.bf_reason = bf_reason;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getBill_name() {	    return this.bill_name;	}	public void setBill_name(String bill_name) {	    this.bill_name=bill_name;	}	public Integer getBill_num() {	    return this.bill_num;	}	public void setBill_num(Integer bill_num) {	    this.bill_num=bill_num;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public String getExpress_code() {	    return this.express_code;	}	public void setExpress_code(String express_code) {	    this.express_code=express_code;	}	public Integer getCon_id() {
		return con_id;
	}
	public void setCon_id(Integer con_id) {
		this.con_id = con_id;
	}
	public String getBillingCycle() {	    return this.billingCycle;	}	public void setBillingCycle(String billingCycle) {	    this.billingCycle=billingCycle;	}	public String getRemarks() {	    return this.remarks;	}	public void setRemarks(String remarks) {	    this.remarks=remarks;	}
}
