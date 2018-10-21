package com.jumbo.wms.model.vmi.godivaData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WSMsgInfo")
public class WSMsgInfo implements Serializable {
    /**
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 4111044735552968246L;
    private String EventID;
    private String TimeStamp;
    private String ServiceType = "dso";

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getBizData() {
        return BizData;
    }

    public void setBizData(String bizData) {
        BizData = bizData;
    }

    private String BizData;


}
