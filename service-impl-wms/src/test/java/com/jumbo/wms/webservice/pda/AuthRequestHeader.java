
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signSalt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sequenceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authRequestHeader", propOrder = {
    "organizationCode",
    "signSalt",
    "sign",
    "sequenceCode",
    "callTime"
})
public class AuthRequestHeader {

    @XmlElement(required = true)
    protected String organizationCode;
    @XmlElement(required = true)
    protected String signSalt;
    @XmlElement(required = true)
    protected String sign;
    @XmlElement(required = true)
    protected String sequenceCode;
    @XmlElement(required = true)
    protected String callTime;

    /**
     * Gets the value of the organizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * Sets the value of the organizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationCode(String value) {
        this.organizationCode = value;
    }

    /**
     * Gets the value of the signSalt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignSalt() {
        return signSalt;
    }

    /**
     * Sets the value of the signSalt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignSalt(String value) {
        this.signSalt = value;
    }

    /**
     * Gets the value of the sign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSign(String value) {
        this.sign = value;
    }

    /**
     * Gets the value of the sequenceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequenceCode() {
        return sequenceCode;
    }

    /**
     * Sets the value of the sequenceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequenceCode(String value) {
        this.sequenceCode = value;
    }

    /**
     * Gets the value of the callTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallTime() {
        return callTime;
    }

    /**
     * Sets the value of the callTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallTime(String value) {
        this.callTime = value;
    }

}
