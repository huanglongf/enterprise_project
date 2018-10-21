package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqmsg")
public class MqMsg implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1546387719527254549L;

    /**
     * 消息头
     */
    private MqMsgHead head;
    
    /**
     * 消息体
     */
    private MqMsgBody body;

    public MqMsgHead getHead() {
        return head;
    }

    public void setHead(MqMsgHead head) {
        this.head = head;
    }

    public MqMsgBody getBody() {
        return body;
    }

    public void setBody(MqMsgBody body) {
        this.body = body;
    }
    
}
