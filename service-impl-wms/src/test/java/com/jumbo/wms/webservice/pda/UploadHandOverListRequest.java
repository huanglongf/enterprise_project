
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadHandOverListRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadHandOverListRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadHandOverListRequestBody" type="{http://webservice.jumbo.com/pda/}uploadHandOverListRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadHandOverListRequest", propOrder = {
    "authRequestHeader",
    "uploadHandOverListRequestBody"
})
public class UploadHandOverListRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadHandOverListRequestBody uploadHandOverListRequestBody;

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
     * Gets the value of the uploadHandOverListRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link UploadHandOverListRequestBody }
     *     
     */
    public UploadHandOverListRequestBody getUploadHandOverListRequestBody() {
        return uploadHandOverListRequestBody;
    }

    /**
     * Sets the value of the uploadHandOverListRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadHandOverListRequestBody }
     *     
     */
    public void setUploadHandOverListRequestBody(UploadHandOverListRequestBody value) {
        this.uploadHandOverListRequestBody = value;
    }

}
