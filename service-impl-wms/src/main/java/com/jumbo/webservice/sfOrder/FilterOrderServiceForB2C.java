package com.jumbo.webservice.sfOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for filterOrderServiceForB2C complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="filterOrderServiceForB2C">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "filterOrderServiceForB2C", propOrder = {"xml"})
public class FilterOrderServiceForB2C {

    protected String xml;

    /**
     * Gets the value of the xml property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXml() {
        return xml;
    }

    /**
     * Sets the value of the xml property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXml(String value) {
        this.xml = value;
    }

}
