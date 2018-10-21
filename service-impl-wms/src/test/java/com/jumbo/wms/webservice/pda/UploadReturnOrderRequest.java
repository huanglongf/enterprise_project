
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadReturnOrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadReturnOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadReturnOrderRequestBody" type="{http://webservice.jumbo.com/pda/}uploadReturnOrderRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadReturnOrderRequest", propOrder = {
    "authRequestHeader",
    "uploadReturnOrderRequestBody"
})
public class UploadReturnOrderRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadReturnOrderRequestBody uploadReturnOrderRequestBody;

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
     * Gets the value of the uploadReturnOrderRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadReturnOrderRequestBody }
     *     
     */
    public UploadReturnOrderRequestBody getUploadReturnOrderRequestBody() {
        return uploadReturnOrderRequestBody;
    }

    /**
     * Sets the value of the uploadReturnOrderRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadReturnOrderRequestBody }
     *     
     */
    public void setUploadReturnOrderRequestBody(UploadReturnOrderRequestBody value) {
        this.uploadReturnOrderRequestBody = value;
    }

}
