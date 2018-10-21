package com.bt.radar.model;

import java.util.Date;

/**
* @ClassName: WarninginfoMaintainMaster
* @Description: TODO(WarninginfoMaintainMaster)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class WarninginfoMaintainMaster {
	
		private String id;			//主键	private Date create_time;			//创建时间	private String create_user;			//创建人	private Date update_time;			//更新时间	private String update_user;			//更新人	private String warningtype_code;			//预警类型代码	private String warningtype_name;			//预警类型名称	private String warning_category;			//预警类别<0：事件>，<1：超时>	private String timeout_mode;			//超时预警时间计时模式	private String initial_level;			//初始级别	private String remark;			//备注	private Integer dl_flag;			//删除标识	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getWarningtype_code() {	    return this.warningtype_code;	}	public void setWarningtype_code(String warningtype_code) {	    this.warningtype_code=warningtype_code;	}	public String getWarningtype_name() {	    return this.warningtype_name;	}	public void setWarningtype_name(String warningtype_name) {	    this.warningtype_name=warningtype_name;	}	public String getWarning_category() {	    return this.warning_category;	}	public void setWarning_category(String warning_category) {	    this.warning_category=warning_category;	}	public String getTimeout_mode() {	    return this.timeout_mode;	}	public void setTimeout_mode(String timeout_mode) {	    this.timeout_mode=timeout_mode;	}	public String getInitial_level() {	    return initial_level;	}	public void setInitial_level(String initial_level) {	    this.initial_level=initial_level;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getDl_flag() {	    return this.dl_flag;	}	public void setDl_flag(Integer dl_flag) {	    this.dl_flag=dl_flag;	}
}
