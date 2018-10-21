
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInventoryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInventoryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInventoryResponseBody" type="{http://webservice.jumbo.com/pda/}getInventoryResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryResponse", propOrder = {
    "authResponseHeader",
    "getInventoryResponseBody"
})
public class GetInventoryResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInventoryResponseBody getInventoryResponseBody;

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
     * Gets the value of the getInventoryResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInventoryResponseBody }
     *     
     */
    public GetInventoryResponseBody getGetInventoryResponseBody() {
        return getInventoryResponseBody;
    }

    /**
     * Sets the value of the getInventoryResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInventoryResponseBody }
     *     
     */
    public void setGetInventoryResponseBody(GetInventoryResponseBody value) {
        this.getInventoryResponseBody = value;
    }

}
