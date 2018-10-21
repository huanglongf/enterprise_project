
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInventoryCheckResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInventoryCheckResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInventoryCheckResponseBody" type="{http://webservice.jumbo.com/pda/}getInventoryCheckResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryCheckResponse", propOrder = {
    "authResponseHeader",
    "getInventoryCheckResponseBody"
})
public class GetInventoryCheckResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInventoryCheckResponseBody getInventoryCheckResponseBody;

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
     * Gets the value of the getInventoryCheckResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInventoryCheckResponseBody }
     *     
     */
    public GetInventoryCheckResponseBody getGetInventoryCheckResponseBody() {
        return getInventoryCheckResponseBody;
    }

    /**
     * Sets the value of the getInventoryCheckResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInventoryCheckResponseBody }
     *     
     */
    public void setGetInventoryCheckResponseBody(GetInventoryCheckResponseBody value) {
        this.getInventoryCheckResponseBody = value;
    }

}
