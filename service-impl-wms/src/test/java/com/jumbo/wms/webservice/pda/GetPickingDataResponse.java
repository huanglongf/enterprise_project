
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPickingDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPickingDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getPickingDataResponseBody" type="{http://webservice.jumbo.com/pda/}getPickingDataResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPickingDataResponse", propOrder = {
    "authResponseHeader",
    "getPickingDataResponseBody"
})
public class GetPickingDataResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetPickingDataResponseBody getPickingDataResponseBody;

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
     * Gets the value of the getPickingDataResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetPickingDataResponseBody }
     *     
     */
    public GetPickingDataResponseBody getGetPickingDataResponseBody() {
        return getPickingDataResponseBody;
    }

    /**
     * Sets the value of the getPickingDataResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPickingDataResponseBody }
     *     
     */
    public void setGetPickingDataResponseBody(GetPickingDataResponseBody value) {
        this.getPickingDataResponseBody = value;
    }

}
