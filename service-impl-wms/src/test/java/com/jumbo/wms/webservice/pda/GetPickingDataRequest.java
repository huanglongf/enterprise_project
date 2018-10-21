
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPickingDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPickingDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getPickingDataRequestBody" type="{http://webservice.jumbo.com/pda/}getPickingDataRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPickingDataRequest", propOrder = {
    "authRequestHeader",
    "getPickingDataRequestBody"
})
public class GetPickingDataRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetPickingDataRequestBody getPickingDataRequestBody;

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
     * Gets the value of the getPickingDataRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetPickingDataRequestBody }
     *     
     */
    public GetPickingDataRequestBody getGetPickingDataRequestBody() {
        return getPickingDataRequestBody;
    }

    /**
     * Sets the value of the getPickingDataRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPickingDataRequestBody }
     *     
     */
    public void setGetPickingDataRequestBody(GetPickingDataRequestBody value) {
        this.getPickingDataRequestBody = value;
    }

}
