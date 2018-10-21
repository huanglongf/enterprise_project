package com.jumbo.webservice.express;

import java.io.Serializable;

public class ExpressAppointTime implements Serializable {
	
	private static final long serialVersionUID = -9069985490447278151L;
	
	private String startTime;	// 指定时间配送/开始时间
	private String endTime;		// 指定时间配送/接受时间
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
