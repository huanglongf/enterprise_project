package com.jumbo.webservice.pda.uploadReturnOrder;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * uploadReturnOrderRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadReturnOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadReturnOrderRequestBody" type="{http://webservice.jumbo.com/pda/}uploadReturnOrderRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadReturnOrderRequest", propOrder = {"authRequestHeader", "uploadReturnOrderRequestBody"})
public class UploadReturnOrderRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2369968428186897035L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadReturnOrderRequestBody uploadReturnOrderRequestBody;

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
     * 获取uploadReturnOrderRequestBody属性的值。
     * 
     * @return possible object is {@link UploadReturnOrderRequestBody }
     * 
     */
    public UploadReturnOrderRequestBody getUploadReturnOrderRequestBody() {
        return uploadReturnOrderRequestBody;
    }

    /**
     * 设置uploadReturnOrderRequestBody属性的值。
     * 
     * @param value allowed object is {@link UploadReturnOrderRequestBody }
     * 
     */
    public void setUploadReturnOrderRequestBody(UploadReturnOrderRequestBody value) {
        this.uploadReturnOrderRequestBody = value;
    }

}
