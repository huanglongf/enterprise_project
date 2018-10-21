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
public class WmsPurchaseOrderRequestDetailList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3464954849579251123L;
    @XmlElement
    private List<WmsPurchaseOrderRequestDetail> item;

    public List<WmsPurchaseOrderRequestDetail> getItem() {
        return item;
    }

    public void setItem(List<WmsPurchaseOrderRequestDetail> item) {
        this.item = item;
    }
}
