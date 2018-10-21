package com.jumbo.mq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqmsgconfirm")
public class MqMsgConfirm {

    /**
     * 消息唯一标识
     */
    private String msgSerialNo;
    
    /**
     * 消息类型
     */
    private String msgType;
    
    /**
     * 处理结果
     * error/success
     */
    private String result;
    
    /**
     * 处理描述
     */
    private String opMsg;
    

    public String getMsgSerialNo() {
        return msgSerialNo;
    }

    public void setMsgSerialNo(String msgSerialNo) {
        this.msgSerialNo = msgSerialNo;
    }
    
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpMsg() {
        return opMsg;
    }

    public void setOpMsg(String opMsg) {
        this.opMsg = opMsg;
    }
    
}
