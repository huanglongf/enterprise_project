
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getReturnOrderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getReturnOrderResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getReturnOrderResponseBody" type="{http://webservice.jumbo.com/pda/}getReturnOrderResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getReturnOrderResponse", propOrder = {
    "authResponseHeader",
    "getReturnOrderResponseBody"
})
public class GetReturnOrderResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetReturnOrderResponseBody getReturnOrderResponseBody;

    /**
     * Gets the value of the authResponseHeader property.
     * 
     * @return
     *     possible object is
     *     {@link AuthResponseHeader }
     *     
     */
    public AuthResponseHeader getAuthResponseHeader() {
        return authResponseHeader;
    }

    /**
     * Sets the value of the authResponseHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthResponseHeader }
     *     
     */
    public void setAuthResponseHeader(AuthResponseHeader value) {
        this.authResponseHeader = value;
    }

    /**
     * Gets the value of the getReturnOrderResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetReturnOrderResponseBody }
     *     
     */
    public GetReturnOrderResponseBody getGetReturnOrderResponseBody() {
        return getReturnOrderResponseBody;
    }

    /**
     * Sets the value of the getReturnOrderResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetReturnOrderResponseBody }
     *     
     */
    public void setGetReturnOrderResponseBody(GetReturnOrderResponseBody value) {
        this.getReturnOrderResponseBody = value;
    }

}
