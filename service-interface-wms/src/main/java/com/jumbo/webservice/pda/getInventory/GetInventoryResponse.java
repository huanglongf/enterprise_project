package com.jumbo.webservice.pda.getInventory;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getInventoryResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInventoryResponseBody" type="{http://webservice.jumbo.com/pda/}getInventoryResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryResponse", propOrder = {"authResponseHeader", "getInventoryResponseBody"})
public class GetInventoryResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7959768867838851543L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInventoryResponseBody getInventoryResponseBody;

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
     * 获取getInventoryResponseBody属性的值。
     * 
     * @return possible object is {@link GetInventoryResponseBody }
     * 
     */
    public GetInventoryResponseBody getGetInventoryResponseBody() {
        return getInventoryResponseBody;
    }

    /**
     * 设置getInventoryResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetInventoryResponseBody }
     * 
     */
    public void setGetInventoryResponseBody(GetInventoryResponseBody value) {
        this.getInventoryResponseBody = value;
    }

}
