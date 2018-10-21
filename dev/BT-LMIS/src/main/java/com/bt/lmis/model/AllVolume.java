package com.bt.lmis.model;

/**
* @ClassName: AllVolume
* @Description: TODO(AllVolume)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class AllVolume {
	
		private Integer sav_id;			//	private String sav_cb_id;			//合同ID	private String sav_section;			//区间	private String sav_price;			//单价	private Integer sav_price_unit;			//单价单位	private Integer sav_status;			//类型0停用1启用	public Integer getSav_id() {	    return this.sav_id;	}	public void setSav_id(Integer sav_id) {	    this.sav_id=sav_id;	}	public String getSav_section() {	    return this.sav_section;	}	public void setSav_section(String sav_section) {	    this.sav_section=sav_section;	}	public String getSav_price() {	    return this.sav_price;	}	public void setSav_price(String sav_price) {	    this.sav_price=sav_price;	}	public Integer getSav_price_unit() {	    return this.sav_price_unit;	}	public void setSav_price_unit(Integer sav_price_unit) {	    this.sav_price_unit=sav_price_unit;	}	public Integer getSav_status() {	    return this.sav_status;	}	public void setSav_status(Integer sav_status) {	    this.sav_status=sav_status;	}
	public String getSav_cb_id() {
		return sav_cb_id;
	}
	public void setSav_cb_id(String sav_cb_id) {
		this.sav_cb_id = sav_cb_id;
	}
}
