package com.bt.radar.model;

import java.util.Date;

public class WaybillWarninginfoDetail {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private Date happen_time;
	private String warning_level;
	private String source;
	private String reason;
	private String warning_category;
	private String warningtype_code;
	private String warningtype_name;
	private String waybill;
    private String express_code;
	private Date efficient_time;
    private String  stop_watch;
    private String wk_flag;
	private String pid;
	private String jkid;
	private String del_flag;
	private String routestatus_code;
	private String wk_id;   //工单主键
    public String getRoutestatus_code() {
		return routestatus_code;
	}
	public void setRoutestatus_code(String routestatus_code) {
		this.routestatus_code = routestatus_code;
	}
	public String getStop_watch() {
		return stop_watch;
	}
	public void setStop_watch(String stop_watch) {
		this.stop_watch = stop_watch;
	}
	public Date getEfficient_time() {
		return efficient_time;
	}
	public void setEfficient_time(Date efficient_time) {
		this.efficient_time = efficient_time;
	}
	private Date route_time;
 
	public Date getRoute_time() {
		return route_time;
	}
	public void setRoute_time(Date route_time) {
		this.route_time = route_time;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
	public String getWarningtype_code() {
		return warningtype_code;
	}
	public void setWarningtype_code(String warningtype_code) {
		this.warningtype_code = warningtype_code;
	}
	public String getWarning_category() {
		return warning_category;
	}
	public void setWarning_category(String warning_category) {
		this.warning_category = warning_category;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Date getHappen_time() {
		return happen_time;
	}
	public void setHappen_time(Date happen_time) {
		this.happen_time = happen_time;
	}
	public String getWarning_level() {
		return warning_level;
	}
	public void setWarning_level(String warning_level) {
		this.warning_level = warning_level;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getJkid() {
		return jkid;
	}
	public void setJkid(String jkid) {
		this.jkid = jkid;
	}
    public String getWarningtype_name() {
		return warningtype_name;
	}
	public void setWarningtype_name(String warningtype_name) {
		this.warningtype_name = warningtype_name;
	}
     public String getWk_flag() {
		return wk_flag;
	}
	public void setWk_flag(String wk_flag) {
		this.wk_flag = wk_flag;
	}
	public String getWk_id() {
		return wk_id;
	}
	public void setWk_id(String wk_id) {
		this.wk_id = wk_id;
	}
}
