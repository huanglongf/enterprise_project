package com.jumbo.webservice.pda.uploadInitiativeMoveOutbound;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * Java class for uploadInitiativeMoveOutboundRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInitiativeMoveOutboundRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadInitiativeMoveOutboundRequestBody" type="{http://webservice.jumbo.com/pda/}uploadInitiativeMoveOutboundRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInitiativeMoveOutboundRequest", propOrder = {"authRequestHeader", "uploadInitiativeMoveOutboundRequestBody"})
public class UploadInitiativeMoveOutboundRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2976479777097803917L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadInitiativeMoveOutboundRequestBody uploadInitiativeMoveOutboundRequestBody;

    /**
     * Gets the value of the authRequestHeader property.
     * 
     * @return possible object is {@link AuthRequestHeader }
     * 
     */
    @Override
    public AuthRequestHeader getAuthRequestHeader() {
        return authRequestHeader;
    }

    /**
     * Sets the value of the authRequestHeader property.
     * 
     * @param value allowed object is {@link AuthRequestHeader }
     * 
     */
    public void setAuthRequestHeader(AuthRequestHeader value) {
        this.authRequestHeader = value;
    }

    /**
     * Gets the value of the uploadInitiativeMoveOutboundRequestBody property.
     * 
     * @return possible object is {@link UploadInitiativeMoveOutboundRequestBody }
     * 
     */
    public UploadInitiativeMoveOutboundRequestBody getUploadInitiativeMoveOutboundRequestBody() {
        return uploadInitiativeMoveOutboundRequestBody;
    }

    /**
     * Sets the value of the uploadInitiativeMoveOutboundRequestBody property.
     * 
     * @param value allowed object is {@link UploadInitiativeMoveOutboundRequestBody }
     * 
     */
    public void setUploadInitiativeMoveOutboundRequestBody(UploadInitiativeMoveOutboundRequestBody value) {
        this.uploadInitiativeMoveOutboundRequestBody = value;
    }

}
