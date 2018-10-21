
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInboundDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInboundDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInboundDataRequestBody" type="{http://webservice.jumbo.com/pda/}getInboundDataRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundDataRequest", propOrder = {
    "authRequestHeader",
    "getInboundDataRequestBody"
})
public class GetInboundDataRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInboundDataRequestBody getInboundDataRequestBody;

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
     * Gets the value of the getInboundDataRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInboundDataRequestBody }
     *     
     */
    public GetInboundDataRequestBody getGetInboundDataRequestBody() {
        return getInboundDataRequestBody;
    }

    /**
     * Sets the value of the getInboundDataRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInboundDataRequestBody }
     *     
     */
    public void setGetInboundDataRequestBody(GetInboundDataRequestBody value) {
        this.getInboundDataRequestBody = value;
    }

}
