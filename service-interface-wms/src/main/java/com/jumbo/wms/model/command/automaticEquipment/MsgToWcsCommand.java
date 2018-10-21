package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.MsgToWcs;

/**
 * @author xiaolong.fei
 * 
 * @createDate 2016年1月19日 下午7:21:46
 */
public class MsgToWcsCommand extends MsgToWcs {

    private static final long serialVersionUID = 3543711598672198181L;

    private Integer msgType; // 消息类型 
    
    private String statusStr; // 转义字符串

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}


}
