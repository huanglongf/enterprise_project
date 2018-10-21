package com.jumbo.webservice.pda.uploadLibraryMovement;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * uploadLibraryMovementRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadLibraryMovementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadLibraryMovementRequestBody" type="{http://webservice.jumbo.com/pda/}uploadLibraryMovementRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadLibraryMovementRequest", propOrder = {"authRequestHeader", "uploadLibraryMovementRequestBody"})
public class UploadLibraryMovementRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1596950963371665407L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadLibraryMovementRequestBody uploadLibraryMovementRequestBody;

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
     * 获取uploadLibraryMovementRequestBody属性的值。
     * 
     * @return possible object is {@link UploadLibraryMovementRequestBody }
     * 
     */
    public UploadLibraryMovementRequestBody getUploadLibraryMovementRequestBody() {
        return uploadLibraryMovementRequestBody;
    }

    /**
     * 设置uploadLibraryMovementRequestBody属性的值。
     * 
     * @param value allowed object is {@link UploadLibraryMovementRequestBody }
     * 
     */
    public void setUploadLibraryMovementRequestBody(UploadLibraryMovementRequestBody value) {
        this.uploadLibraryMovementRequestBody = value;
    }

}
