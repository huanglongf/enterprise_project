package com.jumbo.webservice.pda.uploadInitiativeMoveOutbound;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * Java class for uploadInitiativeMoveOutboundResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInitiativeMoveOutboundResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="uploadInitiativeMoveOutboundResponseBody" type="{http://webservice.jumbo.com/pda/}uploadInitiativeMoveOutboundResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInitiativeMoveOutboundResponse", propOrder = {"authResponseHeader", "uploadInitiativeMoveOutboundResponseBody"})
public class UploadInitiativeMoveOutboundResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2515134846556555855L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected UploadInitiativeMoveOutboundResponseBody uploadInitiativeMoveOutboundResponseBody;

    /**
     * Gets the value of the authResponseHeader property.
     * 
     * @return possible object is {@link AuthResponseHeader }
     * 
     */
    @Override
    public AuthResponseHeader getAuthResponseHeader() {
        return authResponseHeader;
    }

    /**
     * Sets the value of the authResponseHeader property.
     * 
     * @param value allowed object is {@link AuthResponseHeader }
     * 
     */
    public void setAuthResponseHeader(AuthResponseHeader value) {
        this.authResponseHeader = value;
    }

    /**
     * Gets the value of the uploadInitiativeMoveOutboundResponseBody property.
     * 
     * @return possible object is {@link UploadInitiativeMoveOutboundResponseBody }
     * 
     */
    public UploadInitiativeMoveOutboundResponseBody getUploadInitiativeMoveOutboundResponseBody() {
        return uploadInitiativeMoveOutboundResponseBody;
    }

    /**
     * Sets the value of the uploadInitiativeMoveOutboundResponseBody property.
     * 
     * @param value allowed object is {@link UploadInitiativeMoveOutboundResponseBody }
     * 
     */
    public void setUploadInitiativeMoveOutboundResponseBody(UploadInitiativeMoveOutboundResponseBody value) {
        this.uploadInitiativeMoveOutboundResponseBody = value;
    }

}
