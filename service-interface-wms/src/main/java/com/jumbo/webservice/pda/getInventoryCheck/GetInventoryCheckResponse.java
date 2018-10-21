package com.jumbo.webservice.pda.getInventoryCheck;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getInventoryCheckResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryCheckResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInventoryCheckResponseBody" type="{http://webservice.jumbo.com/pda/}getInventoryCheckResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryCheckResponse", propOrder = {"authResponseHeader", "getInventoryCheckResponseBody"})
public class GetInventoryCheckResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6817427850879723837L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInventoryCheckResponseBody getInventoryCheckResponseBody;

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
     * 获取getInventoryCheckResponseBody属性的值。
     * 
     * @return possible object is {@link GetInventoryCheckResponseBody }
     * 
     */
    public GetInventoryCheckResponseBody getGetInventoryCheckResponseBody() {
        return getInventoryCheckResponseBody;
    }

    /**
     * 设置getInventoryCheckResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetInventoryCheckResponseBody }
     * 
     */
    public void setGetInventoryCheckResponseBody(GetInventoryCheckResponseBody value) {
        this.getInventoryCheckResponseBody = value;
    }

}
