package com.jumbo.webservice.pda.getLibraryMovement;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getLibraryMovementResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getLibraryMovementResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getLibraryMovementResponseBody" type="{http://webservice.jumbo.com/pda/}getLibraryMovementResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLibraryMovementResponse", propOrder = {"authResponseHeader", "getLibraryMovementResponseBody"})
public class GetLibraryMovementResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7877883043533941192L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetLibraryMovementResponseBody getLibraryMovementResponseBody;

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
     * 获取getLibraryMovementResponseBody属性的值。
     * 
     * @return possible object is {@link GetLibraryMovementResponseBody }
     * 
     */
    public GetLibraryMovementResponseBody getGetLibraryMovementResponseBody() {
        return getLibraryMovementResponseBody;
    }

    /**
     * 设置getLibraryMovementResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetLibraryMovementResponseBody }
     * 
     */
    public void setGetLibraryMovementResponseBody(GetLibraryMovementResponseBody value) {
        this.getLibraryMovementResponseBody = value;
    }

}
