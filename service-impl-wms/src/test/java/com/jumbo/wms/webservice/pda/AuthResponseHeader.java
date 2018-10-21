
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authResponseHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authResponseHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signSalt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sequenceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serverSequenceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callbackTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authResponseHeader", propOrder = {
    "signSalt",
    "sign",
    "sequenceCode",
    "serverSequenceCode",
    "callbackTime"
})
public class AuthResponseHeader {

    @XmlElement(required = true)
    protected String signSalt;
    @XmlElement(required = true)
    protected String sign;
    @XmlElement(required = true)
    protected String sequenceCode;
    @XmlElement(required = true)
    protected String serverSequenceCode;
    @XmlElement(required = true)
    protected String callbackTime;

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
     * Gets the value of the serverSequenceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerSequenceCode() {
        return serverSequenceCode;
    }

    /**
     * Sets the value of the serverSequenceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerSequenceCode(String value) {
        this.serverSequenceCode = value;
    }

    /**
     * Gets the value of the callbackTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallbackTime() {
        return callbackTime;
    }

    /**
     * Sets the value of the callbackTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallbackTime(String value) {
        this.callbackTime = value;
    }

}
