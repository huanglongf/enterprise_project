package com.jumbo.webservice.pda.getLibraryMovement;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getLibraryMovementRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getLibraryMovementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getLibraryMovementRequestBody" type="{http://webservice.jumbo.com/pda/}getLibraryMovementRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLibraryMovementRequest", propOrder = {"authRequestHeader", "getLibraryMovementRequestBody"})
public class GetLibraryMovementRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4697634472849803993L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetLibraryMovementRequestBody getLibraryMovementRequestBody;

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
     * 获取getLibraryMovementRequestBody属性的值。
     * 
     * @return possible object is {@link GetLibraryMovementRequestBody }
     * 
     */
    public GetLibraryMovementRequestBody getGetLibraryMovementRequestBody() {
        return getLibraryMovementRequestBody;
    }

    /**
     * 设置getLibraryMovementRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetLibraryMovementRequestBody }
     * 
     */
    public void setGetLibraryMovementRequestBody(GetLibraryMovementRequestBody value) {
        this.getLibraryMovementRequestBody = value;
    }

}
