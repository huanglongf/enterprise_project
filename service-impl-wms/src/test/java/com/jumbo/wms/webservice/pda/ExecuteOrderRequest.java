
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for executeOrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="executeOrderRequestBody" type="{http://webservice.jumbo.com/pda/}executeOrderRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeOrderRequest", propOrder = {
    "authRequestHeader",
    "executeOrderRequestBody"
})
public class ExecuteOrderRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected ExecuteOrderRequestBody executeOrderRequestBody;

    /**
     * Gets the value of the authRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link AuthRequestHeader }
     *     
     */
    public AuthRequestHeader getAuthRequestHeader() {
        return authRequestHeader;
    }

    /**
     * Sets the value of the authRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthRequestHeader }
     *     
     */
    public void setAuthRequestHeader(AuthRequestHeader value) {
        this.authRequestHeader = value;
    }

    /**
     * Gets the value of the executeOrderRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link ExecuteOrderRequestBody }
     *     
     */
    public ExecuteOrderRequestBody getExecuteOrderRequestBody() {
        return executeOrderRequestBody;
    }

    /**
     * Sets the value of the executeOrderRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecuteOrderRequestBody }
     *     
     */
    public void setExecuteOrderRequestBody(ExecuteOrderRequestBody value) {
        this.executeOrderRequestBody = value;
    }

}
