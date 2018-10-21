package com.jumbo.webservice.sfNew;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sfexpressServiceResponse", propOrder = {"_return"})
public class SfexpressServiceResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2197827384243737933L;
    @XmlElement(name = "return")
    protected String _return;

    public String getReturn() {
        return _return;
    }

    public void setReturn(String value) {
        this._return = value;
    }

}
