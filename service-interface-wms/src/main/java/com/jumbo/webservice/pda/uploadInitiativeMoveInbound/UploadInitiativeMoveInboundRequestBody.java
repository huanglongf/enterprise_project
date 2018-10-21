package com.jumbo.webservice.pda.uploadInitiativeMoveInbound;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.LibraryMovementInOut;


/**
 * <p>
 * Java class for uploadInitiativeMoveInboundRequestBody complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInitiativeMoveInboundRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="slipCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inbound" type="{http://webservice.jumbo.com/pda/}libraryMovementInOut"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInitiativeMoveInboundRequestBody", propOrder = {"code", "slipCode", "uniqCode", "inbound"})
public class UploadInitiativeMoveInboundRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6810288167891110667L;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String slipCode;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected LibraryMovementInOut inbound;

    /**
     * Gets the value of the code property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the slipCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSlipCode() {
        return slipCode;
    }

    /**
     * Sets the value of the slipCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSlipCode(String value) {
        this.slipCode = value;
    }

    /**
     * Gets the value of the uniqCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the inbound property.
     * 
     * @return possible object is {@link LibraryMovementInOut }
     * 
     */
    public LibraryMovementInOut getInbound() {
        return inbound;
    }

    /**
     * Sets the value of the inbound property.
     * 
     * @param value allowed object is {@link LibraryMovementInOut }
     * 
     */
    public void setInbound(LibraryMovementInOut value) {
        this.inbound = value;
    }

}
