package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SerialNumber implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8436430822357173436L;
    private List<String> serial_number;

    public List<String> getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(List<String> serialNumber) {
        serial_number = serialNumber;
    }
}
