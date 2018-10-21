
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInboundOnShelvesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInboundOnShelvesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInboundOnShelvesResponseBody" type="{http://webservice.jumbo.com/pda/}getInboundOnShelvesResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundOnShelvesResponse", propOrder = {
    "authResponseHeader",
    "getInboundOnShelvesResponseBody"
})
public class GetInboundOnShelvesResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInboundOnShelvesResponseBody getInboundOnShelvesResponseBody;

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
     * Gets the value of the getInboundOnShelvesResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInboundOnShelvesResponseBody }
     *     
     */
    public GetInboundOnShelvesResponseBody getGetInboundOnShelvesResponseBody() {
        return getInboundOnShelvesResponseBody;
    }

    /**
     * Sets the value of the getInboundOnShelvesResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInboundOnShelvesResponseBody }
     *     
     */
    public void setGetInboundOnShelvesResponseBody(GetInboundOnShelvesResponseBody value) {
        this.getInboundOnShelvesResponseBody = value;
    }

}
