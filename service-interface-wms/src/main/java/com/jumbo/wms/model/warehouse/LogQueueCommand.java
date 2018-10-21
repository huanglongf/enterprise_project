package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class LogQueueCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5500967816077040910L;
	private String channelCode;
	private String whCode;
	private String skuCode;
	private int direction;
	private String qty;
	private String opTime;
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	
	

}
