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
@XmlRootElement(name = "wmsCancelSailOrderResponse")
public class WmsCancelSailOrderResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 327404414253755962L;
    @XmlElement
    private String orderid;
    @XmlElement
    private String result;
    @XmlElement
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
