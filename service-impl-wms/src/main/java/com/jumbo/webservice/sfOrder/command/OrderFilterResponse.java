package com.jumbo.webservice.sfOrder.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serviceResponse")
public class OrderFilterResponse {

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
