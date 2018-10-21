package com.jumbo.webservice.pda.getInventoryCheck;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getInventoryCheckRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryCheckRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInventoryCheckRequestBody" type="{http://webservice.jumbo.com/pda/}getInventoryCheckRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryCheckRequest", propOrder = {"authRequestHeader", "getInventoryCheckRequestBody"})
public class GetInventoryCheckRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 406322704233771460L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInventoryCheckRequestBody getInventoryCheckRequestBody;

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
     * 获取getInventoryCheckRequestBody属性的值。
     * 
     * @return possible object is {@link GetInventoryCheckRequestBody }
     * 
     */
    public GetInventoryCheckRequestBody getGetInventoryCheckRequestBody() {
        return getInventoryCheckRequestBody;
    }

    /**
     * 设置getInventoryCheckRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetInventoryCheckRequestBody }
     * 
     */
    public void setGetInventoryCheckRequestBody(GetInventoryCheckRequestBody value) {
        this.getInventoryCheckRequestBody = value;
    }

}
