package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

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
@XmlRootElement(name = "wmsMerchantCatalogBatchResponse")
public class WmsMerchantCatalogBatchResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2762068989521069395L;
    @XmlElement
    private String result;
    @XmlElement
    private String ramark;
    private List<ItemList> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRamark() {
        return ramark;
    }

    public void setRamark(String ramark) {
        this.ramark = ramark;
    }

    public List<ItemList> getList() {
        return list;
    }

    public void setList(List<ItemList> list) {
        this.list = list;
    }

}
