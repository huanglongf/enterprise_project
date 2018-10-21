package com.bt.login.controller.form;

public class FunctionButtonQueryParam{
	
		private String id;			//	private java.util.Date create_time;			//创建时间	private String create_by;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private Integer is_deleted;			//逻辑删除标志 0-未删除 1-已删除	private Integer is_disabled;			//启停标志 1-已禁用 0-未禁用	private Integer version;			//版本号	private String pwr_org;			//权限架构	private String fb_id;			//功能菜单/按钮ID	private String fb_name;			//功能菜单/按钮名称	private String fb_pid;			//上级菜单ID	private String fb_lv;			//菜单按钮级别	private String fb_type;			//类型：0菜单/1按钮	private Integer fb_seq;			//显示顺序	private String fb_url;			//访问路径
		public Integer getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}
	public Integer getIs_disabled() {
		return is_disabled;
	}
	public void setIs_disabled(Integer is_disabled) {
		this.is_disabled = is_disabled;
	}
	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_by() {	    return this.create_by;	}	public void setCreate_by(String create_by) {	    this.create_by=create_by;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public Integer getVersion() {	    return this.version;	}	public void setVersion(Integer version) {	    this.version=version;	}	public String getPwr_org() {	    return this.pwr_org;	}	public void setPwr_org(String pwr_org) {	    this.pwr_org=pwr_org;	}	public String getFb_id() {	    return this.fb_id;	}	public void setFb_id(String fb_id) {	    this.fb_id=fb_id;	}	public String getFb_name() {	    return this.fb_name;	}	public void setFb_name(String fb_name) {	    this.fb_name=fb_name;	}	public String getFb_pid() {	    return this.fb_pid;	}	public void setFb_pid(String fb_pid) {	    this.fb_pid=fb_pid;	}	public String getFb_lv() {	    return this.fb_lv;	}	public void setFb_lv(String fb_lv) {	    this.fb_lv=fb_lv;	}	public String getFb_type() {	    return this.fb_type;	}	public void setFb_type(String fb_type) {	    this.fb_type=fb_type;	}	public Integer getFb_seq() {	    return this.fb_seq;	}	public void setFb_seq(Integer fb_seq) {	    this.fb_seq=fb_seq;	}	public String getFb_url() {	    return this.fb_url;	}	public void setFb_url(String fb_url) {	    this.fb_url=fb_url;	}
	
}
