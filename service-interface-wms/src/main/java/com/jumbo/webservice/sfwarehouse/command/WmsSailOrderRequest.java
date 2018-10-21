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
@XmlRootElement(name = "wmsSailOrderRequest")
public class WmsSailOrderRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3234854918156573559L;
    @XmlElement
    private String checkword;
    @XmlElement
    private WmsSailOrderRequestHeader header;
    @XmlElement
    private WmsSailOrderRequestDetailList detailList;

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public WmsSailOrderRequestHeader getHeader() {
        return header;
    }

    public void setHeader(WmsSailOrderRequestHeader header) {
        this.header = header;
    }

    public WmsSailOrderRequestDetailList getDetailList() {
        return detailList;
    }

    public void setDetailList(WmsSailOrderRequestDetailList detailList) {
        this.detailList = detailList;
    }


}
