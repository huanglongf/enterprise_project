package com.jumbo.webservice.pda.uploadHandOverList;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * uploadHandOverListRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadHandOverListRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="uploadHandOverListRequestBody" type="{http://webservice.jumbo.com/pda/}uploadHandOverListRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadHandOverListRequest", propOrder = {"authRequestHeader", "uploadHandOverListRequestBody"})
public class UploadHandOverListRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3582489824284679218L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected UploadHandOverListRequestBody uploadHandOverListRequestBody;

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
     * 获取uploadHandOverListRequestBody属性的值。
     * 
     * @return possible object is {@link UploadHandOverListRequestBody }
     * 
     */
    public UploadHandOverListRequestBody getUploadHandOverListRequestBody() {
        return uploadHandOverListRequestBody;
    }

    /**
     * 设置uploadHandOverListRequestBody属性的值。
     * 
     * @param value allowed object is {@link UploadHandOverListRequestBody }
     * 
     */
    public void setUploadHandOverListRequestBody(UploadHandOverListRequestBody value) {
        this.uploadHandOverListRequestBody = value;
    }

}
