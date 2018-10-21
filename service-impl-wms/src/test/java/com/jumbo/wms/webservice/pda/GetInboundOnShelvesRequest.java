
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInboundOnShelvesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInboundOnShelvesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInboundOnShelvesRequestBody" type="{http://webservice.jumbo.com/pda/}getInboundOnShelvesRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundOnShelvesRequest", propOrder = {
    "authRequestHeader",
    "getInboundOnShelvesRequestBody"
})
public class GetInboundOnShelvesRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInboundOnShelvesRequestBody getInboundOnShelvesRequestBody;

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
     * Gets the value of the getInboundOnShelvesRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInboundOnShelvesRequestBody }
     *     
     */
    public GetInboundOnShelvesRequestBody getGetInboundOnShelvesRequestBody() {
        return getInboundOnShelvesRequestBody;
    }

    /**
     * Sets the value of the getInboundOnShelvesRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInboundOnShelvesRequestBody }
     *     
     */
    public void setGetInboundOnShelvesRequestBody(GetInboundOnShelvesRequestBody value) {
        this.getInboundOnShelvesRequestBody = value;
    }

}
