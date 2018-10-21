
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInboundReceiveRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInboundReceiveRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadInboundReceiveRequestBody" type="{http://webservice.jumbo.com/pda/}uploadInboundReceiveRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInboundReceiveRequest", propOrder = {
    "authRequestHeader",
    "uploadInboundReceiveRequestBody"
})
public class UploadInboundReceiveRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadInboundReceiveRequestBody uploadInboundReceiveRequestBody;

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
     * Gets the value of the uploadInboundReceiveRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadInboundReceiveRequestBody }
     *     
     */
    public UploadInboundReceiveRequestBody getUploadInboundReceiveRequestBody() {
        return uploadInboundReceiveRequestBody;
    }

    /**
     * Sets the value of the uploadInboundReceiveRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadInboundReceiveRequestBody }
     *     
     */
    public void setUploadInboundReceiveRequestBody(UploadInboundReceiveRequestBody value) {
        this.uploadInboundReceiveRequestBody = value;
    }

}
