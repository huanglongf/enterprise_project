
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInventoryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInventoryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInventoryRequestBody" type="{http://webservice.jumbo.com/pda/}getInventoryRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryRequest", propOrder = {
    "authRequestHeader",
    "getInventoryRequestBody"
})
public class GetInventoryRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInventoryRequestBody getInventoryRequestBody;

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
     * Gets the value of the getInventoryRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInventoryRequestBody }
     *     
     */
    public GetInventoryRequestBody getGetInventoryRequestBody() {
        return getInventoryRequestBody;
    }

    /**
     * Sets the value of the getInventoryRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInventoryRequestBody }
     *     
     */
    public void setGetInventoryRequestBody(GetInventoryRequestBody value) {
        this.getInventoryRequestBody = value;
    }

}
