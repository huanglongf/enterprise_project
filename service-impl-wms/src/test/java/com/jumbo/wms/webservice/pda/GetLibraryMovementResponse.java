
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLibraryMovementResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLibraryMovementResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getLibraryMovementResponseBody" type="{http://webservice.jumbo.com/pda/}getLibraryMovementResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLibraryMovementResponse", propOrder = {
    "authResponseHeader",
    "getLibraryMovementResponseBody"
})
public class GetLibraryMovementResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetLibraryMovementResponseBody getLibraryMovementResponseBody;

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
     * Gets the value of the getLibraryMovementResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryMovementResponseBody }
     *     
     */
    public GetLibraryMovementResponseBody getGetLibraryMovementResponseBody() {
        return getLibraryMovementResponseBody;
    }

    /**
     * Sets the value of the getLibraryMovementResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryMovementResponseBody }
     *     
     */
    public void setGetLibraryMovementResponseBody(GetLibraryMovementResponseBody value) {
        this.getLibraryMovementResponseBody = value;
    }

}
