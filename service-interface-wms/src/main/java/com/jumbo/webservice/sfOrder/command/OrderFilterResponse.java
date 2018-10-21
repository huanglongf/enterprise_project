package com.jumbo.webservice.sfOrder.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serviceResponse")
public class OrderFilterResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7440138812495111818L;
    public static final String FLAG_SUCCESS = "success";
    public static final String FLAG_FAIL = "fail";

    @XmlElement(required = true, name = "flag")
    private String flag = FLAG_SUCCESS;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
