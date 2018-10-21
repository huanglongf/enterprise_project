package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

public class OperatorQueryParam extends QueryParameter {
	private String isError;
	private String jkCode;
	private String bat_id;
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getJkCode() {
		return jkCode;
	}
	public void setJkCode(String jkCode) {
		this.jkCode = jkCode;
	}
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}	
}
