package com.bt.lmis.model;

import java.util.List;

/**
 * @Title:InsuranceRedisBean
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月29日上午11:56:10
 */
public class InsuranceRedisBean {
	private String key;
	private List<InsuranceEC> value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<InsuranceEC> getValue() {
		return value;
	}
	public void setValue(List<InsuranceEC> value) {
		this.value = value;
	}
	
}
