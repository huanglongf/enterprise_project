
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadLibraryMovementRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadLibraryMovementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadLibraryMovementRequestBody" type="{http://webservice.jumbo.com/pda/}uploadLibraryMovementRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadLibraryMovementRequest", propOrder = {
    "authRequestHeader",
    "uploadLibraryMovementRequestBody"
})
public class UploadLibraryMovementRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadLibraryMovementRequestBody uploadLibraryMovementRequestBody;

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
     * Gets the value of the uploadLibraryMovementRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadLibraryMovementRequestBody }
     *     
     */
    public UploadLibraryMovementRequestBody getUploadLibraryMovementRequestBody() {
        return uploadLibraryMovementRequestBody;
    }

    /**
     * Sets the value of the uploadLibraryMovementRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadLibraryMovementRequestBody }
     *     
     */
    public void setUploadLibraryMovementRequestBody(UploadLibraryMovementRequestBody value) {
        this.uploadLibraryMovementRequestBody = value;
    }

}
