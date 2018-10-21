package com.bt.radar.model;

/**
* @ClassName: Routecode
* @Description: TODO(Routecode)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class Routecode {
	
		private String id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String transport_code;			//物流服务商	private String status_code;			//状态代码	private String status;			//状态	private Integer flag;			//标识	private String remark;			//备注	private Integer dl_flag;			//删除标识	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getTransport_code() {	    return this.transport_code;	}	public void setTransport_code(String transport_code) {	    this.transport_code=transport_code;	}	public String getStatus_code() {	    return this.status_code;	}	public void setStatus_code(String status_code) {	    this.status_code=status_code;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public Integer getFlag() {	    return this.flag;	}	public void setFlag(Integer flag) {	    this.flag=flag;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getDl_flag() {	    return this.dl_flag;	}	public void setDl_flag(Integer dl_flag) {	    this.dl_flag=dl_flag;	}
}
