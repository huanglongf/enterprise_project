package com.bt.radar.model;

import java.util.Date;

/**
* @ClassName: WarninglevelList
* @Description: TODO(WarninglevelList)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class WarninglevelList {
	private String id;			//主键	private Date create_time;			//创建时间	private String create_user;			//创建人	private Date update_time;			//更新时间	private String update_user;			//更新人	private String levelup_time;			//升级时间	private String levelup_level;			//升级 达到级别	private String warningtype_code;			//	private Integer dl_flag;			//删除标识
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
	public String getLevelup_time() {
		return levelup_time;
	}
	public void setLevelup_time(String levelup_time) {
		this.levelup_time = levelup_time;
	}
	public String getLevelup_level() {
		return levelup_level;
	}
	public void setLevelup_level(String levelup_level) {
		this.levelup_level = levelup_level;
	}
	public String getWarningtype_code() {
		return warningtype_code;
	}
	public void setWarningtype_code(String warningtype_code) {
		this.warningtype_code = warningtype_code;
	}
	public Integer getDl_flag() {
		return dl_flag;
	}
	public void setDl_flag(Integer dl_flag) {
		this.dl_flag = dl_flag;
	}}
