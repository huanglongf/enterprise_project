package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

public class TotalAreaQueryParam extends QueryParameter {
	
		private Integer sta_id;			//主键	private Integer sta_cb_id;			//合同ID	private String sta_section;			//区间	private String sta_price;			//单价	private Integer sta_price_unit;			//	private Integer sta_status;			//状态0停用1启用	public Integer getSta_id() {	    return this.sta_id;	}	public void setSta_id(Integer sta_id) {	    this.sta_id=sta_id;	}	public Integer getSta_cb_id() {	    return this.sta_cb_id;	}	public void setSta_cb_id(Integer sta_cb_id) {	    this.sta_cb_id=sta_cb_id;	}	public String getSta_section() {	    return this.sta_section;	}	public void setSta_section(String sta_section) {	    this.sta_section=sta_section;	}	public String getSta_price() {	    return this.sta_price;	}	public void setSta_price(String sta_price) {	    this.sta_price=sta_price;	}	public Integer getSta_price_unit() {	    return this.sta_price_unit;	}	public void setSta_price_unit(Integer sta_price_unit) {	    this.sta_price_unit=sta_price_unit;	}	public Integer getSta_status() {	    return this.sta_status;	}	public void setSta_status(Integer sta_status) {	    this.sta_status=sta_status;	}
	
}
