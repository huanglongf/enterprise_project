
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInitiativeMoveOutboundRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInitiativeMoveOutboundRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="slipCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outbound" type="{http://webservice.jumbo.com/pda/}libraryMovementInOut"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInitiativeMoveOutboundRequestBody", propOrder = {
    "slipCode",
    "uniqCode",
    "userName",
    "outbound"
})
public class UploadInitiativeMoveOutboundRequestBody {

    @XmlElement(required = true)
    protected String slipCode;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected LibraryMovementInOut outbound;

    /**
     * Gets the value of the slipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSlipCode() {
        return slipCode;
    }

    /**
     * Sets the value of the slipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSlipCode(String value) {
        this.slipCode = value;
    }

    /**
     * Gets the value of the uniqCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the outbound property.
     * 
     * @return
     *     possible object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public LibraryMovementInOut getOutbound() {
        return outbound;
    }

    /**
     * Sets the value of the outbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public void setOutbound(LibraryMovementInOut value) {
        this.outbound = value;
    }

}
