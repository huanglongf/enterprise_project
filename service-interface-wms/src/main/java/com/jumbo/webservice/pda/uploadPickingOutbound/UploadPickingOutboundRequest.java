package com.jumbo.webservice.pda.uploadPickingOutbound;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * uploadPickingOutboundRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadPickingOutboundRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadPickingOutboundRequestBody" type="{http://webservice.jumbo.com/pda/}uploadPickingOutboundRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadPickingOutboundRequest", propOrder = {"authRequestHeader", "uploadPickingOutboundRequestBody"})
public class UploadPickingOutboundRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6387851543789749308L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadPickingOutboundRequestBody uploadPickingOutboundRequestBody;

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
     * 获取uploadPickingOutboundRequestBody属性的值。
     * 
     * @return possible object is {@link UploadPickingOutboundRequestBody }
     * 
     */
    public UploadPickingOutboundRequestBody getUploadPickingOutboundRequestBody() {
        return uploadPickingOutboundRequestBody;
    }

    /**
     * 设置uploadPickingOutboundRequestBody属性的值。
     * 
     * @param value allowed object is {@link UploadPickingOutboundRequestBody }
     * 
     */
    public void setUploadPickingOutboundRequestBody(UploadPickingOutboundRequestBody value) {
        this.uploadPickingOutboundRequestBody = value;
    }

}
