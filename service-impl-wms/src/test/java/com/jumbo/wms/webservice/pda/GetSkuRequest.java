
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSkuRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSkuRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getSkuRequestBody" type="{http://webservice.jumbo.com/pda/}getSkuRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuRequest", propOrder = {
    "authRequestHeader",
    "getSkuRequestBody"
})
public class GetSkuRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetSkuRequestBody getSkuRequestBody;

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
     * Gets the value of the getSkuRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetSkuRequestBody }
     *     
     */
    public GetSkuRequestBody getGetSkuRequestBody() {
        return getSkuRequestBody;
    }

    /**
     * Sets the value of the getSkuRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSkuRequestBody }
     *     
     */
    public void setGetSkuRequestBody(GetSkuRequestBody value) {
        this.getSkuRequestBody = value;
    }

}
