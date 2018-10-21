package com.jumbo.webservice.sfOrder.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderFilterResult")
public class OrderFilterResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5465566675201605152L;
    /**
     * 订单号
     */
    @XmlElement(required = true, name = "orderid")
    private String orderid;
    @XmlElement(required = true, name = "isAccept")
    private OrderFilterResultAccept accept;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public OrderFilterResultAccept getAccept() {
        return accept;
    }

    public void setAccept(OrderFilterResultAccept accept) {
        this.accept = accept;
    }
}
