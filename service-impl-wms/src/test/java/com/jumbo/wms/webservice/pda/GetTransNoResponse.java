
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTransNoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTransNoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getTransNoResponseBody" type="{http://webservice.jumbo.com/pda/}getTransNoResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTransNoResponse", propOrder = {
    "authResponseHeader",
    "getTransNoResponseBody"
})
public class GetTransNoResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetTransNoResponseBody getTransNoResponseBody;

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
     * Gets the value of the getTransNoResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetTransNoResponseBody }
     *     
     */
    public GetTransNoResponseBody getGetTransNoResponseBody() {
        return getTransNoResponseBody;
    }

    /**
     * Sets the value of the getTransNoResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetTransNoResponseBody }
     *     
     */
    public void setGetTransNoResponseBody(GetTransNoResponseBody value) {
        this.getTransNoResponseBody = value;
    }

}
