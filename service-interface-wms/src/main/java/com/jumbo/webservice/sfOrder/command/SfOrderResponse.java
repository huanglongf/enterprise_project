package com.jumbo.webservice.sfOrder.command;

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
    private static final long serialVersionUID = 3239018647264568157L;
    @XmlElement(name = "orderid")
    private String orderid;
    @XmlElement(name = "result")
    private SfOrderResultResponse result;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public SfOrderResultResponse getResult() {
        return result;
    }

    public void setResult(SfOrderResultResponse result) {
        this.result = result;
    }

}
