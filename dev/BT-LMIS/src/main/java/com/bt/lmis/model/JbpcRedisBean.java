package com.bt.lmis.model;

import java.util.List;

public class JbpcRedisBean {
	private String key;
	private List<JibanpaoConditionEx> value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<JibanpaoConditionEx> getValue() {
		return value;
	}
	public void setValue(List<JibanpaoConditionEx> value) {
		this.value = value;
	}
	
}
