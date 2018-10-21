package com.jumbo.rmi.warehouse;

import java.io.Serializable;

public class OrderLineGift implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5593715196174174793L;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 礼品类型
	 * 10,赠送礼品卡;
	 * 30,商品特殊印制;
	 * 50,商品特殊包装;
	 * 20,Coach保修卡
	 */
	private int type;
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
