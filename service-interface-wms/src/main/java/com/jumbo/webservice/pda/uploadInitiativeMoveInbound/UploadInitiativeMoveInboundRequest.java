package com.jumbo.webservice.pda.uploadInitiativeMoveInbound;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * Java class for uploadInitiativeMoveInboundRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInitiativeMoveInboundRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadInitiativeMoveInboundRequestBody" type="{http://webservice.jumbo.com/pda/}uploadInitiativeMoveInboundRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInitiativeMoveInboundRequest", propOrder = {"authRequestHeader", "uploadInitiativeMoveInboundRequestBody"})
public class UploadInitiativeMoveInboundRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -982417830899687489L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadInitiativeMoveInboundRequestBody uploadInitiativeMoveInboundRequestBody;

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
     * Gets the value of the uploadInitiativeMoveInboundRequestBody property.
     * 
     * @return possible object is {@link UploadInitiativeMoveInboundRequestBody }
     * 
     */
    public UploadInitiativeMoveInboundRequestBody getUploadInitiativeMoveInboundRequestBody() {
        return uploadInitiativeMoveInboundRequestBody;
    }

    /**
     * Sets the value of the uploadInitiativeMoveInboundRequestBody property.
     * 
     * @param value allowed object is {@link UploadInitiativeMoveInboundRequestBody }
     * 
     */
    public void setUploadInitiativeMoveInboundRequestBody(UploadInitiativeMoveInboundRequestBody value) {
        this.uploadInitiativeMoveInboundRequestBody = value;
    }

}
