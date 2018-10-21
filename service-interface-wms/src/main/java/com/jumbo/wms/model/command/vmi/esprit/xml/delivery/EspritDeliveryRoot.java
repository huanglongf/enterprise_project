package com.jumbo.wms.model.command.vmi.esprit.xml.delivery;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"header", "deliveries"})
@XmlRootElement(name = "espDelivery")
public class EspritDeliveryRoot implements Serializable {



    private static final long serialVersionUID = -3407279188479497288L;
    @XmlElement(required = true, name = "header")
    public EspritDeliveryHeader header;
    @XmlElement(required = true, name = "deliveries")
    public EspritDeliveries deliveries;

    public EspritDeliveryHeader getHeader() {
        return header;
    }

    public void setHeader(EspritDeliveryHeader value) {
        this.header = value;
    }

    public EspritDeliveries getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(EspritDeliveries value) {
        this.deliveries = value;
    }
}
