package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wmsSailOrderStatusQueryResponse")
public class WmsSailOrderStatusQueryResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6955193142863477874L;
    @XmlElement
    private String result;
    @XmlElement
    private String remark;
    @XmlElement
    private SailOrder order;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SailOrder getOrder() {
        return order;
    }

    public void setOrder(SailOrder order) {
        this.order = order;
    }
}
