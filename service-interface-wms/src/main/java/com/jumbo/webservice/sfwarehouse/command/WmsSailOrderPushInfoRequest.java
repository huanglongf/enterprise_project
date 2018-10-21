package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wmsSailOrderPushInfo")
public class WmsSailOrderPushInfoRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3858209789747163777L;
    private WmsSailOrderPushInfoRequestHeader header;
    private WmsSailOrderPushInfoRequestDetailList detailList;
    private WmsSailOrderPushInfoRequestContainerList containerList;

    public WmsSailOrderPushInfoRequestHeader getHeader() {
        return header;
    }

    public void setHeader(WmsSailOrderPushInfoRequestHeader header) {
        this.header = header;
    }

    public WmsSailOrderPushInfoRequestDetailList getDetailList() {
        return detailList;
    }

    public void setDetailList(WmsSailOrderPushInfoRequestDetailList detailList) {
        this.detailList = detailList;
    }

    public WmsSailOrderPushInfoRequestContainerList getContainerList() {
        return containerList;
    }

    public void setContainerList(WmsSailOrderPushInfoRequestContainerList containerList) {
        this.containerList = containerList;
    }
}
