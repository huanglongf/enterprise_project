
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadPickingOutboundRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadPickingOutboundRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadPickingOutboundRequestBody" type="{http://webservice.jumbo.com/pda/}uploadPickingOutboundRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadPickingOutboundRequest", propOrder = {
    "authRequestHeader",
    "uploadPickingOutboundRequestBody"
})
public class UploadPickingOutboundRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadPickingOutboundRequestBody uploadPickingOutboundRequestBody;

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
     * Gets the value of the uploadPickingOutboundRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadPickingOutboundRequestBody }
     *     
     */
    public UploadPickingOutboundRequestBody getUploadPickingOutboundRequestBody() {
        return uploadPickingOutboundRequestBody;
    }

    /**
     * Sets the value of the uploadPickingOutboundRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadPickingOutboundRequestBody }
     *     
     */
    public void setUploadPickingOutboundRequestBody(UploadPickingOutboundRequestBody value) {
        this.uploadPickingOutboundRequestBody = value;
    }

}
