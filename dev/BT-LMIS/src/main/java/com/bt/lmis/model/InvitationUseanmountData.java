package com.bt.lmis.model;

/**
* @ClassName: InvitationUseanmountData
* @Description: TODO(InvitationUseanmountData)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class InvitationUseanmountData {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private java.math.BigDecimal application_amount;			//实际使用量	private String sku_number;			//耗材编码	private java.util.Date start_time;			//起始时间	private java.util.Date end_time;			//结束时间	private Integer store_id;			//所属店铺id
	private String store_name;   //店铺名称	private Integer settle_flag;			//结算标识	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public java.math.BigDecimal getApplication_amount() {	    return this.application_amount;	}	public void setApplication_amount(java.math.BigDecimal application_amount) {	    this.application_amount=application_amount;	}	public String getSku_number() {	    return this.sku_number;	}	public void setSku_number(String sku_number) {	    this.sku_number=sku_number;	}	public java.util.Date getStart_time() {	    return this.start_time;	}	public void setStart_time(java.util.Date start_time) {	    this.start_time=start_time;	}	public java.util.Date getEnd_time() {	    return this.end_time;	}	public void setEnd_time(java.util.Date end_time) {	    this.end_time=end_time;	}	public Integer getStore_id() {	    return this.store_id;	}	public void setStore_id(Integer store_id) {	    this.store_id=store_id;	}	public Integer getSettle_flag() {	    return this.settle_flag;	}	public void setSettle_flag(Integer settle_flag) {	    this.settle_flag=settle_flag;	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
}
