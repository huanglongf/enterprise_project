package com.jumbo.webservice.sf.model;

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
@XmlRootElement(name = "cancelOrderResponse")
public class SfOrderCancelResponse {

    public final static String RESULT_SUEECEE = "1";
    public final static String RESULT_FIALED = "2";

    @XmlElement(name = "orderid")
    private String orderid;
    @XmlElement(name = "result")
    private String result;
    @XmlElement(name = "remark")
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
