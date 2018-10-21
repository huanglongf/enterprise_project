package com.bt.lmis.model;

import java.util.List;

/**
 * @Title:NextPriceInternalRedisBean
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月29日上午10:49:15
 */
public class NextPriceInternalRedisBean {
	private String key;
	private List<NextPriceInternal> value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<NextPriceInternal> getValue() {
		return value;
	}
	public void setValue(List<NextPriceInternal> value) {
		this.value = value;
	}
}
