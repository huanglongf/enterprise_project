
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInventoryRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInventoryRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="skuBarCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryRequestBody", propOrder = {
    "uniqCode",
    "skuBarCode",
    "location"
})
public class GetInventoryRequestBody {

    @XmlElement(required = true)
    protected String uniqCode;
    protected String skuBarCode;
    protected String location;

    /**
     * Gets the value of the uniqCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the skuBarCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuBarCode() {
        return skuBarCode;
    }

    /**
     * Sets the value of the skuBarCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuBarCode(String value) {
        this.skuBarCode = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

}
