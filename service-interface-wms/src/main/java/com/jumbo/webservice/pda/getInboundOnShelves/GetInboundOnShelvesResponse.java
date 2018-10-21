package com.jumbo.webservice.pda.getInboundOnShelves;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getInboundOnShelvesResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInboundOnShelvesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInboundOnShelvesResponseBody" type="{http://webservice.jumbo.com/pda/}getInboundOnShelvesResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundOnShelvesResponse", propOrder = {"authResponseHeader", "getInboundOnShelvesResponseBody"})
public class GetInboundOnShelvesResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 979015833079711757L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInboundOnShelvesResponseBody getInboundOnShelvesResponseBody;

    /**
     * 获取authResponseHeader属性的值。
     * 
     * @return possible object is {@link AuthResponseHeader }
     * 
     */
    @Override
    public AuthResponseHeader getAuthResponseHeader() {
        return authResponseHeader;
    }

    /**
     * 设置authResponseHeader属性的值。
     * 
     * @param value allowed object is {@link AuthResponseHeader }
     * 
     */
    public void setAuthResponseHeader(AuthResponseHeader value) {
        this.authResponseHeader = value;
    }

    /**
     * 获取getInboundOnShelvesResponseBody属性的值。
     * 
     * @return possible object is {@link GetInboundOnShelvesResponseBody }
     * 
     */
    public GetInboundOnShelvesResponseBody getGetInboundOnShelvesResponseBody() {
        return getInboundOnShelvesResponseBody;
    }

    /**
     * 设置getInboundOnShelvesResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetInboundOnShelvesResponseBody }
     * 
     */
    public void setGetInboundOnShelvesResponseBody(GetInboundOnShelvesResponseBody value) {
        this.getInboundOnShelvesResponseBody = value;
    }

}
