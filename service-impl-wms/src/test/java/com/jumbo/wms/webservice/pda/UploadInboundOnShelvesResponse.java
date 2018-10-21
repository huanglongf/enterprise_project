
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInboundOnShelvesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInboundOnShelvesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="baseResponseBody" type="{http://webservice.jumbo.com/pda/}baseResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInboundOnShelvesResponse", propOrder = {
    "authResponseHeader",
    "baseResponseBody"
})
public class UploadInboundOnShelvesResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected BaseResponseBody baseResponseBody;

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
     * Gets the value of the baseResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link BaseResponseBody }
     *     
     */
    public BaseResponseBody getBaseResponseBody() {
        return baseResponseBody;
    }

    /**
     * Sets the value of the baseResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseResponseBody }
     *     
     */
    public void setBaseResponseBody(BaseResponseBody value) {
        this.baseResponseBody = value;
    }

}
