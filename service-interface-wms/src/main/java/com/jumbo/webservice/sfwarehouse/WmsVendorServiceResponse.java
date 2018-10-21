package com.jumbo.webservice.sfwarehouse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for wmsVendorServiceResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wmsVendorServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wmsVendorServiceResponse", propOrder = {"_return"})
public class WmsVendorServiceResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3507080489828207843L;
    @XmlElement(name = "return")
    protected String _return;

    /**
     * Gets the value of the return property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setReturn(String value) {
        this._return = value;
    }

}
