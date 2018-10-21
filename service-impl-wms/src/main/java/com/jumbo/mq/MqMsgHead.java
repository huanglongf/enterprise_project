package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqmsghead")
public class MqMsgHead implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8438413423820524869L;

    /**
     * 请求帐号
     */
    private String account;

    /**
     * 请求时间,格式：yyyyMMddHH24miss
     */
    private String callTime;

    /**
     * 签名
     */
    private String sign;

    /**
     * 消息流水号(发送端消息唯一标识)
     */
    private String serialNo;

    /**
     * 消息来源
     */
    private String source;

    /**
     * 消息类型
     * MqConstants中定义相关常量
     */
    private String msgType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    
}
