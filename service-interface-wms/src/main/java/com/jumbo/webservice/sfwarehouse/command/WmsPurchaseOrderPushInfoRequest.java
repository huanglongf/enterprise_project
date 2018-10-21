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
@XmlRootElement(name = "wmsPurchaseOrderPushInfo")
public class WmsPurchaseOrderPushInfoRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5031859447025616960L;
    @XmlElement
    private WmsPurchaseOrderPushInfoRequestHeader header;
    @XmlElement
    private WmsPurchaseOrderPushInfoRequestDetailList detailList;

    public WmsPurchaseOrderPushInfoRequestHeader getHeader() {
        return header;
    }

    public void setHeader(WmsPurchaseOrderPushInfoRequestHeader header) {
        this.header = header;
    }

    public WmsPurchaseOrderPushInfoRequestDetailList getDetailList() {
        return detailList;
    }

    public void setDetailList(WmsPurchaseOrderPushInfoRequestDetailList detailList) {
        this.detailList = detailList;
    }
}
