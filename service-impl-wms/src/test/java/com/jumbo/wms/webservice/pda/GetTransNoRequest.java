
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTransNoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTransNoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getTransNoRequestBody" type="{http://webservice.jumbo.com/pda/}getTransNoRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTransNoRequest", propOrder = {
    "authRequestHeader",
    "getTransNoRequestBody"
})
public class GetTransNoRequest {

    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetTransNoRequestBody getTransNoRequestBody;

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
     * Gets the value of the getTransNoRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link GetTransNoRequestBody }
     *     
     */
    public GetTransNoRequestBody getGetTransNoRequestBody() {
        return getTransNoRequestBody;
    }

    /**
     * Sets the value of the getTransNoRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetTransNoRequestBody }
     *     
     */
    public void setGetTransNoRequestBody(GetTransNoRequestBody value) {
        this.getTransNoRequestBody = value;
    }

}
