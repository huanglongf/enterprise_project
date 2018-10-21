
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInboundOnShelvesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInboundOnShelvesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadInboundOnShelvesRequestBody" type="{http://webservice.jumbo.com/pda/}uploadInboundOnShelvesRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInboundOnShelvesRequest", propOrder = {
    "authRequestHeader",
    "uploadInboundOnShelvesRequestBody"
})
public class UploadInboundOnShelvesRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadInboundOnShelvesRequestBody uploadInboundOnShelvesRequestBody;

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
     * Gets the value of the uploadInboundOnShelvesRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadInboundOnShelvesRequestBody }
     *     
     */
    public UploadInboundOnShelvesRequestBody getUploadInboundOnShelvesRequestBody() {
        return uploadInboundOnShelvesRequestBody;
    }

    /**
     * Sets the value of the uploadInboundOnShelvesRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadInboundOnShelvesRequestBody }
     *     
     */
    public void setUploadInboundOnShelvesRequestBody(UploadInboundOnShelvesRequestBody value) {
        this.uploadInboundOnShelvesRequestBody = value;
    }

}
