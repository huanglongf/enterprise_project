package com.jumbo.webservice.pda.getInboundData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getInboundDataResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInboundDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getInboundDataResponseBody" type="{http://webservice.jumbo.com/pda/}getInboundDataResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundDataResponse", propOrder = {"authResponseHeader", "getInboundDataResponseBody"})
public class GetInboundDataResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2601970281226203814L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetInboundDataResponseBody getInboundDataResponseBody;

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
     * 获取getInboundDataResponseBody属性的值。
     * 
     * @return possible object is {@link GetInboundDataResponseBody }
     * 
     */
    public GetInboundDataResponseBody getGetInboundDataResponseBody() {
        return getInboundDataResponseBody;
    }

    /**
     * 设置getInboundDataResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetInboundDataResponseBody }
     * 
     */
    public void setGetInboundDataResponseBody(GetInboundDataResponseBody value) {
        this.getInboundDataResponseBody = value;
    }

}
