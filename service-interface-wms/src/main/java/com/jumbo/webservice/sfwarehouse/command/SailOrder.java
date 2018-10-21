package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class SailOrder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6248624932147928781L;
    @XmlAttribute
    private String orderid;
    @XmlAttribute
    private String waybillno;
    @XmlElement
    private List<SailOrderStep> steps;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public List<SailOrderStep> getSteps() {
        return steps;
    }

    public void setSteps(List<SailOrderStep> steps) {
        this.steps = steps;
    }
}
