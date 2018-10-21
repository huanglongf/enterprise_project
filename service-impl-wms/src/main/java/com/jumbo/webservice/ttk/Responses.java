package com.jumbo.webservice.ttk;

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
 *         &lt;element ref="{}logisticProviderID"/>
 *         &lt;element ref="{}responseItems"/>
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
    "logisticProviderID",
    "responseItems"
})
@XmlRootElement(name = "responses")
public class Responses {

    @XmlElement(required = true)
    protected String logisticProviderID;
    @XmlElement(required = true)
    protected ResponseItems responseItems;

    /**
     * Gets the value of the logisticProviderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    /**
     * Sets the value of the logisticProviderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogisticProviderID(String value) {
        this.logisticProviderID = value;
    }

    /**
     * Gets the value of the responseItems property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseItems }
     *     
     */
    public ResponseItems getResponseItems() {
        return responseItems;
    }

    /**
     * Sets the value of the responseItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseItems }
     *     
     */
    public void setResponseItems(ResponseItems value) {
        this.responseItems = value;
    }

}
