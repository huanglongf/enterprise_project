package com.jumbo.wms.model.command.vmi.esprit.xml.delivery;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"delivery"})
@XmlRootElement(name = "deliveries")
public class EspritDeliveries implements Serializable {



    private static final long serialVersionUID = 1198975214465445417L;
    @XmlElement(required = true, name = "delivery")
    protected List<EspritDelivery> delivery;

    public List<EspritDelivery> getDelivery() {
        return this.delivery;
    }

    public void setDelivery(List<EspritDelivery> delivery) {
        this.delivery = delivery;
    }



}
