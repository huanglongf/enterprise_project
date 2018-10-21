package com.jumbo.webservice.sfNew;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sfexpressService", propOrder = {"arg0"})
public class SfexpressService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1395712071219810624L;
    protected String arg0;

    public String getArg0() {
        return arg0;
    }

    public void setArg0(String value) {
        this.arg0 = value;
    }

}
