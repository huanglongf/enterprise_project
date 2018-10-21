package com.jumbo.webservice.pda.getReturnOrder;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getReturnOrderResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getReturnOrderResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getReturnOrderResponseBody" type="{http://webservice.jumbo.com/pda/}getReturnOrderResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getReturnOrderResponse", propOrder = {"authResponseHeader", "getReturnOrderResponseBody"})
public class GetReturnOrderResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7908971390101714809L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetReturnOrderResponseBody getReturnOrderResponseBody;

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
     * 获取getReturnOrderResponseBody属性的值。
     * 
     * @return possible object is {@link GetReturnOrderResponseBody }
     * 
     */
    public GetReturnOrderResponseBody getGetReturnOrderResponseBody() {
        return getReturnOrderResponseBody;
    }

    /**
     * 设置getReturnOrderResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetReturnOrderResponseBody }
     * 
     */
    public void setGetReturnOrderResponseBody(GetReturnOrderResponseBody value) {
        this.getReturnOrderResponseBody = value;
    }

}
