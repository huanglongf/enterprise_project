package com.jumbo.webservice.sf.model;

import java.io.Serializable;

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
@XmlRootElement(name = "orderResponse")
public class SfOrderResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6864815299812677701L;
    @XmlElement(name = "orderid")
    private String orderid;
    @XmlElement(name = "result")
    private String result;
    @XmlElement(name = "mailno")
    private String mailno;
    @XmlElement(name = "remark")
    private String remark;
    @XmlElement(name = "distCode")
    private String distCode;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
