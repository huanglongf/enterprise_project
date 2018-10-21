package com.jumbo.webservice.pda.getReturnOrder;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getReturnOrderRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getReturnOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getReturnOrderRequestBody" type="{http://webservice.jumbo.com/pda/}getReturnOrderRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getReturnOrderRequest", propOrder = {"authRequestHeader", "getReturnOrderRequestBody"})
public class GetReturnOrderRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8809286287573760906L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetReturnOrderRequestBody getReturnOrderRequestBody;

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
     * 获取getReturnOrderRequestBody属性的值。
     * 
     * @return possible object is {@link GetReturnOrderRequestBody }
     * 
     */
    public GetReturnOrderRequestBody getGetReturnOrderRequestBody() {
        return getReturnOrderRequestBody;
    }

    /**
     * 设置getReturnOrderRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetReturnOrderRequestBody }
     * 
     */
    public void setGetReturnOrderRequestBody(GetReturnOrderRequestBody value) {
        this.getReturnOrderRequestBody = value;
    }

}
