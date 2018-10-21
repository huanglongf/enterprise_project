package com.jumbo.webservice.pda.getSku;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuResponse", propOrder = {"authResponseHeader", "getSkuResponseBody"})
public class GetSkuResponse implements AuthResponse, Serializable {

    private static final long serialVersionUID = 3894431938829523803L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetSkuResponseBody getSkuResponseBody;

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
     * Gets the value of the getSkuResponseBody property.
     * 
     * @return possible object is {@link GetSkuResponseBody }
     * 
     */
    public GetSkuResponseBody getGetSkuResponseBody() {
        return getSkuResponseBody;
    }

    /**
     * Sets the value of the getSkuResponseBody property.
     * 
     * @param value allowed object is {@link GetSkuResponseBody }
     * 
     */
    public void setGetSkuResponseBody(GetSkuResponseBody value) {
        this.getSkuResponseBody = value;
    }

}
