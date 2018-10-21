package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsPurchaseOrderPushInfoRequestDetailList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6808887967633761351L;
    @XmlElement
    private List<WmsPurchaseOrderPushInfoRequestItem> item;

    public List<WmsPurchaseOrderPushInfoRequestItem> getItem() {
        return item;
    }

    public void setItem(List<WmsPurchaseOrderPushInfoRequestItem> item) {
        this.item = item;
    }
}
