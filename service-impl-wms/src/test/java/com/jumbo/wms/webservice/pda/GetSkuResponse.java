
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSkuResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSkuResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getSkuResponseBody" type="{http://webservice.jumbo.com/pda/}getSkuResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuResponse", propOrder = {
    "authResponseHeader",
    "getSkuResponseBody"
})
public class GetSkuResponse {

    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetSkuResponseBody getSkuResponseBody;

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
     * Gets the value of the getSkuResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetSkuResponseBody }
     *     
     */
    public GetSkuResponseBody getGetSkuResponseBody() {
        return getSkuResponseBody;
    }

    /**
     * Sets the value of the getSkuResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSkuResponseBody }
     *     
     */
    public void setGetSkuResponseBody(GetSkuResponseBody value) {
        this.getSkuResponseBody = value;
    }

}
