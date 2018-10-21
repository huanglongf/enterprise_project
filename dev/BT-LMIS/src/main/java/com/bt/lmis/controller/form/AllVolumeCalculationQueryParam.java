package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

public class AllVolumeCalculationQueryParam extends QueryParameter {
	
		private Integer savc_id;			//	private Integer savc_cb_id;			//合同ID	private String savc_section;			//区间	private String stvc_price;			//单价	private Integer stvc_price_unit;			//单价单位	private Integer stvc_status;			//类型0停用1启用	public Integer getSavc_id() {	    return this.savc_id;	}	public void setSavc_id(Integer savc_id) {	    this.savc_id=savc_id;	}	public Integer getSavc_cb_id() {	    return this.savc_cb_id;	}	public void setSavc_cb_id(Integer savc_cb_id) {	    this.savc_cb_id=savc_cb_id;	}	public String getSavc_section() {	    return this.savc_section;	}	public void setSavc_section(String savc_section) {	    this.savc_section=savc_section;	}	public String getStvc_price() {	    return this.stvc_price;	}	public void setStvc_price(String stvc_price) {	    this.stvc_price=stvc_price;	}	public Integer getStvc_price_unit() {	    return this.stvc_price_unit;	}	public void setStvc_price_unit(Integer stvc_price_unit) {	    this.stvc_price_unit=stvc_price_unit;	}	public Integer getStvc_status() {	    return this.stvc_status;	}	public void setStvc_status(Integer stvc_status) {	    this.stvc_status=stvc_status;	}
	
}
