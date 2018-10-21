
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLibraryMovementRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLibraryMovementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getLibraryMovementRequestBody" type="{http://webservice.jumbo.com/pda/}getLibraryMovementRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLibraryMovementRequest", propOrder = {
    "authRequestHeader",
    "getLibraryMovementRequestBody"
})
public class GetLibraryMovementRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetLibraryMovementRequestBody getLibraryMovementRequestBody;

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
     * Gets the value of the getLibraryMovementRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryMovementRequestBody }
     *     
     */
    public GetLibraryMovementRequestBody getGetLibraryMovementRequestBody() {
        return getLibraryMovementRequestBody;
    }

    /**
     * Sets the value of the getLibraryMovementRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryMovementRequestBody }
     *     
     */
    public void setGetLibraryMovementRequestBody(GetLibraryMovementRequestBody value) {
        this.getLibraryMovementRequestBody = value;
    }

}
