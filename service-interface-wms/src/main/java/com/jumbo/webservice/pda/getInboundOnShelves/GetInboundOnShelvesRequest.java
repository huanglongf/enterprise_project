package com.jumbo.webservice.pda.getInboundOnShelves;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getInboundOnShelvesRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInboundOnShelvesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInboundOnShelvesRequestBody" type="{http://webservice.jumbo.com/pda/}getInboundOnShelvesRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundOnShelvesRequest", propOrder = {"authRequestHeader", "getInboundOnShelvesRequestBody"})
public class GetInboundOnShelvesRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7230029895751172892L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInboundOnShelvesRequestBody getInboundOnShelvesRequestBody;

    /**
     * 获取authRequestHeader属性的值。
     * 
     * @return possible object is {@link AuthRequestHeader }
     * 
     */
    @Override
    public AuthRequestHeader getAuthRequestHeader() {
        return authRequestHeader;
    }

    /**
     * 设置authRequestHeader属性的值。
     * 
     * @param value allowed object is {@link AuthRequestHeader }
     * 
     */
    public void setAuthRequestHeader(AuthRequestHeader value) {
        this.authRequestHeader = value;
    }

    /**
     * 获取getInboundOnShelvesRequestBody属性的值。
     * 
     * @return possible object is {@link GetInboundOnShelvesRequestBody }
     * 
     */
    public GetInboundOnShelvesRequestBody getGetInboundOnShelvesRequestBody() {
        return getInboundOnShelvesRequestBody;
    }

    /**
     * 设置getInboundOnShelvesRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetInboundOnShelvesRequestBody }
     * 
     */
    public void setGetInboundOnShelvesRequestBody(GetInboundOnShelvesRequestBody value) {
        this.getInboundOnShelvesRequestBody = value;
    }

}
