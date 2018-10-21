
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInboundDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInboundDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInboundDataResponseBody" type="{http://webservice.jumbo.com/pda/}getInboundDataResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundDataResponse", propOrder = {
    "authResponseHeader",
    "getInboundDataResponseBody"
})
public class GetInboundDataResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInboundDataResponseBody getInboundDataResponseBody;

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
     * Gets the value of the getInboundDataResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInboundDataResponseBody }
     *     
     */
    public GetInboundDataResponseBody getGetInboundDataResponseBody() {
        return getInboundDataResponseBody;
    }

    /**
     * Sets the value of the getInboundDataResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInboundDataResponseBody }
     *     
     */
    public void setGetInboundDataResponseBody(GetInboundDataResponseBody value) {
        this.getInboundDataResponseBody = value;
    }

}
