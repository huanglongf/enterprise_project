package com.bt.workOrder.model;

/**
* @ClassName: GenerationRule
* @Description: TODO(GenerationRule)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class GenerationRule {
	private String id;			//
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
	private String wk_name;
	public String getEw_type_code() {
		return ew_type_code;
	}
	public void setEw_type_code(String ew_type_code) {
		this.ew_type_code = ew_type_code;
	}
}