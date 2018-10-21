package com.bt.lmis.model;

/**
* @ClassName: ItemType
* @Description: TODO(ItemType)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class ItemType {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private String itemtype_code;			//商品类型代码	private String itemtype_name;			//商品类型名称	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getItemtype_code() {	    return this.itemtype_code;	}	public void setItemtype_code(String itemtype_code) {	    this.itemtype_code=itemtype_code;	}	public String getItemtype_name() {	    return this.itemtype_name;	}	public void setItemtype_name(String itemtype_name) {	    this.itemtype_name=itemtype_name;	}
}
