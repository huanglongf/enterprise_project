
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInventoryCheckRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInventoryCheckRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInventoryCheckRequestBody" type="{http://webservice.jumbo.com/pda/}getInventoryCheckRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryCheckRequest", propOrder = {
    "authRequestHeader",
    "getInventoryCheckRequestBody"
})
public class GetInventoryCheckRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInventoryCheckRequestBody getInventoryCheckRequestBody;

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
     * Gets the value of the getInventoryCheckRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetInventoryCheckRequestBody }
     *     
     */
    public GetInventoryCheckRequestBody getGetInventoryCheckRequestBody() {
        return getInventoryCheckRequestBody;
    }

    /**
     * Sets the value of the getInventoryCheckRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInventoryCheckRequestBody }
     *     
     */
    public void setGetInventoryCheckRequestBody(GetInventoryCheckRequestBody value) {
        this.getInventoryCheckRequestBody = value;
    }

}
