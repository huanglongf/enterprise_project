//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.18 at 10:43:14 ���� CST 
//


package com.jumbo.webservice.sf.tw.inventory.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InventoryChangeResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inventoryChangeResponse"
})
@XmlRootElement(name = "Body")
public class InventoryRespBody {

    @XmlElement(name = "InventoryChangeResponse", required = true)
    protected InventoryChangeResponse inventoryChangeResponse;

    /**
     * Gets the value of the inventoryChangeResponse property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryChangeResponse }
     *     
     */
    public InventoryChangeResponse getInventoryChangeResponse() {
        return inventoryChangeResponse;
    }

    /**
     * Sets the value of the inventoryChangeResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryChangeResponse }
     *     
     */
    public void setInventoryChangeResponse(InventoryChangeResponse value) {
        this.inventoryChangeResponse = value;
    }

}
