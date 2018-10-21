package com.jumbo.webservice.pda.getSku;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuRequest", propOrder = {"authRequestHeader", "getSkuRequestBody"})
public class GetSkuRequest implements AuthRequest, Serializable {

    private static final long serialVersionUID = 2800688107049017855L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetSkuRequestBody getSkuRequestBody;

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
     * Gets the value of the getSkuRequestBody property.
     * 
     * @return possible object is {@link GetSkuRequestBody }
     * 
     */
    public GetSkuRequestBody getGetSkuRequestBody() {
        return getSkuRequestBody;
    }

    /**
     * Sets the value of the getSkuRequestBody property.
     * 
     * @param value allowed object is {@link GetSkuRequestBody }
     * 
     */
    public void setgetSkuRequestBody(GetSkuRequestBody value) {
        this.getSkuRequestBody = value;
    }

}
