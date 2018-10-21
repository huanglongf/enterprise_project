package com.jumbo.webservice.express;

import java.io.Serializable;

public class ExpressRoute implements Serializable {
	
	private static final long serialVersionUID = 3891447522457848169L;
	
	private String lineId; 		// 路由行标识
	private String remark;		// 路由节点描述
	private String acceptTime;	// 节点时间 格式：yyyyMMddHHmiss
	private String opCode;		// 操作编码
	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

}
