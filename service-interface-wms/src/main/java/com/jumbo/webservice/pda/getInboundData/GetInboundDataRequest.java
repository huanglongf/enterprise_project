package com.jumbo.webservice.pda.getInboundData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getInboundDataRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInboundDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInboundDataRequestBody" type="{http://webservice.jumbo.com/pda/}getInboundDataRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInboundDataRequest", propOrder = {"authRequestHeader", "getInboundDataRequestBody"})
public class GetInboundDataRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4309305956578161350L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInboundDataRequestBody getInboundDataRequestBody;

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
     * 获取getInboundDataRequestBody属性的值。
     * 
     * @return possible object is {@link GetInboundDataRequestBody }
     * 
     */
    public GetInboundDataRequestBody getGetInboundDataRequestBody() {
        return getInboundDataRequestBody;
    }

    /**
     * 设置getInboundDataRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetInboundDataRequestBody }
     * 
     */
    public void setGetInboundDataRequestBody(GetInboundDataRequestBody value) {
        this.getInboundDataRequestBody = value;
    }

}
