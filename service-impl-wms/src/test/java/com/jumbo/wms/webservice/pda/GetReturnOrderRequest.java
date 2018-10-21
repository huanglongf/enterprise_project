
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getReturnOrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getReturnOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getReturnOrderRequestBody" type="{http://webservice.jumbo.com/pda/}getReturnOrderRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getReturnOrderRequest", propOrder = {
    "authRequestHeader",
    "getReturnOrderRequestBody"
})
public class GetReturnOrderRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetReturnOrderRequestBody getReturnOrderRequestBody;

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
     * Gets the value of the getReturnOrderRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetReturnOrderRequestBody }
     *     
     */
    public GetReturnOrderRequestBody getGetReturnOrderRequestBody() {
        return getReturnOrderRequestBody;
    }

    /**
     * Sets the value of the getReturnOrderRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetReturnOrderRequestBody }
     *     
     */
    public void setGetReturnOrderRequestBody(GetReturnOrderRequestBody value) {
        this.getReturnOrderRequestBody = value;
    }

}
