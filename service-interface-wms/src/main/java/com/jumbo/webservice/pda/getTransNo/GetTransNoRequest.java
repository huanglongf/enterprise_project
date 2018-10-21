package com.jumbo.webservice.pda.getTransNo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * Java class for getTransNoRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
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
@XmlType(name = "getTransNoRequest", propOrder = {"authRequestHeader", "getTransNoRequestBody"})
public class GetTransNoRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1678950599054954424L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetTransNoRequestBody getTransNoRequestBody;

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
     * Gets the value of the getTransNoRequestBody property.
     * 
     * @return possible object is {@link GetTransNoRequestBody }
     * 
     */
    public GetTransNoRequestBody getGetTransNoRequestBody() {
        return getTransNoRequestBody;
    }

    /**
     * Sets the value of the getTransNoRequestBody property.
     * 
     * @param value allowed object is {@link GetTransNoRequestBody }
     * 
     */
    public void setGetTransNoRequestBody(GetTransNoRequestBody value) {
        this.getTransNoRequestBody = value;
    }

}
