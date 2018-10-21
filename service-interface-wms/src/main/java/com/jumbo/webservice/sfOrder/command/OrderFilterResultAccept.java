package com.jumbo.webservice.sfOrder.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "isAccept")
public class OrderFilterResultAccept implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -309226298750353214L;
    public static final String FLAG_SUCCESS = "success";
    public static final String FLAG_FAIL = "fail";

    @XmlElement(required = true, name = "flag")
    private String flag;
    @XmlElement(name = "distCode")
    private String distCode;
    @XmlElement(name = "mailNo")
    private String mailNo;
    @XmlElement(name = "reason")
    private String reason;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
