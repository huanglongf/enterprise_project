package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 采购入库请求实体
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wmsPurchaseOrderRequest")
public class WmsPurchaseOrderRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7722012779371497732L;
    @XmlElement
    private String checkword;
    @XmlElement
    private WmsPurchaseOrderRequestHeader header;
    @XmlElement
    private WmsPurchaseOrderRequestDetailList detailList;

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public WmsPurchaseOrderRequestHeader getHeader() {
        return header;
    }

    public void setHeader(WmsPurchaseOrderRequestHeader header) {
        this.header = header;
    }

    public WmsPurchaseOrderRequestDetailList getDetailList() {
        return detailList;
    }

    public void setDetailList(WmsPurchaseOrderRequestDetailList detailList) {
        this.detailList = detailList;
    }


}
