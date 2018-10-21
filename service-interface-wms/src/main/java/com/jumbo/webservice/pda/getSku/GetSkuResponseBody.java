package com.jumbo.webservice.pda.getSku;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuResponseBody", propOrder = {"skuId", "barcode", "skuname"})
public class GetSkuResponseBody implements Serializable {

    private static final long serialVersionUID = -1546631856905194561L;

    @XmlElement(required = true)
    protected Long skuId;
    @XmlElement(required = true)
    protected String barcode;
    @XmlElement(required = true)
    protected String skuname;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }


}
