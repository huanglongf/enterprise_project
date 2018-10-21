package com.bt.workOrder.model;

/**
* @ClassName: GenerationRule
* @Description: TODO(GenerationRule)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class GenerationRule {
	private String id;			//	private java.util.Date create_time;			//	private java.util.Date update_time;			//	private String create_user;			//	private String update_user;			//	private String ew_type_code;			//	private String ew_level;			//	private String ew_flag;			//是否生成工单(0否,1是)	private String wk_type;			//	private String wk_level;			//
	private String warningtype_name;
	private String wk_type_level_id;
	public String getWk_type_level_id() {
		return wk_type_level_id;
	}
	public void setWk_type_level_id(String wk_type_level_id) {
		this.wk_type_level_id = wk_type_level_id;
	}
	public String getEw_type_level_id() {
		return ew_type_level_id;
	}
	public void setEw_type_level_id(String ew_type_level_id) {
		this.ew_type_level_id = ew_type_level_id;
	}
	private String ew_type_level_id;
	public String getWarningtype_name() {
		return warningtype_name;
	}
	public void setWarningtype_name(String warningtype_name) {
		this.warningtype_name = warningtype_name;
	}

	public String getWk_name() {
		return wk_name;
	}
	public void setWk_name(String wk_name) {
		this.wk_name = wk_name;
	}
	private String wk_name;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getEw_level() {	    return this.ew_level;	}	public void setEw_level(String ew_level) {	    this.ew_level=ew_level;	}	public String getEw_flag() {	    return this.ew_flag;	}	public void setEw_flag(String ew_flag) {	    this.ew_flag=ew_flag;	}	public String getWk_type() {	    return this.wk_type;	}	public void setWk_type(String wk_type) {	    this.wk_type=wk_type;	}	public String getWk_level() {	    return this.wk_level;	}	public void setWk_level(String wk_level) {	    this.wk_level=wk_level;	}
	public String getEw_type_code() {
		return ew_type_code;
	}
	public void setEw_type_code(String ew_type_code) {
		this.ew_type_code = ew_type_code;
	}
}
