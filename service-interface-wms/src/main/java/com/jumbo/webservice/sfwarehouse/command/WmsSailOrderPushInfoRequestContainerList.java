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
public class WmsSailOrderPushInfoRequestContainerList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5634800044104681245L;
    @XmlElement
    private List<WmsSailOrderPushInfoRequestContainerItem> item;

    public List<WmsSailOrderPushInfoRequestContainerItem> getItem() {
        return item;
    }

    public void setItem(List<WmsSailOrderPushInfoRequestContainerItem> item) {
        this.item = item;
    }
}
