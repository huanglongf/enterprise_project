package com.jumbo.webservice.pda.getSku;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for getSkuRequestBody complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSkuRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuRequestBody", propOrder = {"barcode"})
public class GetSkuRequestBody implements Serializable {

    private static final long serialVersionUID = 7652236574868460138L;
    @XmlElement(required = true)
    protected String barcode;

    /**
     * Gets the value of the startTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStartTime(String value) {
        this.barcode = value;
    }

}
