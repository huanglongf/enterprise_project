package com.jumbo.webservice.pda.uploadInboundReceive;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;
import com.jumbo.webservice.pda.BaseResponseBody;


/**
 * <p>
 * uploadInboundReceiveResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadInboundReceiveResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="baseResponseBody" type="{http://webservice.jumbo.com/pda/}baseResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInboundReceiveResponse", propOrder = {"authResponseHeader", "baseResponseBody"})
public class UploadInboundReceiveResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7614337699536174347L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected BaseResponseBody baseResponseBody;

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
     * 获取baseResponseBody属性的值。
     * 
     * @return possible object is {@link BaseResponseBody }
     * 
     */
    public BaseResponseBody getBaseResponseBody() {
        return baseResponseBody;
    }

    /**
     * 设置baseResponseBody属性的值。
     * 
     * @param value allowed object is {@link BaseResponseBody }
     * 
     */
    public void setBaseResponseBody(BaseResponseBody value) {
        this.baseResponseBody = value;
    }

}
