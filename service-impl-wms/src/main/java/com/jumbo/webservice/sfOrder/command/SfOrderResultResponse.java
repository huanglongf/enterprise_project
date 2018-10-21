package com.jumbo.webservice.sfOrder.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 顺风下单实体
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class SfOrderResultResponse {

    public final static String STATUS_SUEECSS = "1";
    @XmlElement(name = "status")
    private String status;
    @XmlElement(name = "mailno")
    private String mailno;
    @XmlElement(name = "remark")
    private String remark;
    @XmlElement(name = "distCode")
    private String distCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

}
