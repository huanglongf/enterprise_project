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
public class WmsSailOrderRequestDetailList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5092988856698437479L;
    @XmlElement
    private List<WmsSailOrderRequestDetail> item;

    public List<WmsSailOrderRequestDetail> getItem() {
        return item;
    }

    public void setItem(List<WmsSailOrderRequestDetail> item) {
        this.item = item;
    }
}
