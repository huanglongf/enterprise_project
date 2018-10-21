package com.jumbo.webservice.ttk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}txLogisticID"/>
 *         &lt;element ref="{}success"/>
 *         &lt;element ref="{}reason"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "txLogisticID",
    "success",
    "reason"
})
@XmlRootElement(name = "response")
public class Response {

    @XmlElement(required = true)
    protected String txLogisticID;
    protected boolean success;
    @XmlElement(required = true)
    protected String reason;

    /**
     * Gets the value of the txLogisticID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxLogisticID() {
        return txLogisticID;
    }

    /**
     * Sets the value of the txLogisticID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxLogisticID(String value) {
        this.txLogisticID = value;
    }

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

}
