package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqmsgbody")
public class MqMsgBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7305836407570770703L;
    
    private Object msgContent;

    public Object getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(Object msgContent) {
        this.msgContent = msgContent;
    }
    
}
