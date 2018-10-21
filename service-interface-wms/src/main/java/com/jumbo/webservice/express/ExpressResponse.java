package com.jumbo.webservice.express;

import java.io.Serializable;

public class ExpressResponse implements Serializable {
	
	private static final long serialVersionUID = 6507482334222377750L;
	
	protected String status;	// 状态  1：成功  0：失败
	protected String msg;		// 消息
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean getIsSuccess() {
		if ("1".equals(status)) {
            return true;
        } else {
            return false;
        }
	}
}
