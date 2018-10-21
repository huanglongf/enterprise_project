package com.jumbo.webservice.pda.getInventory;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * getInventoryRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="getInventoryRequestBody" type="{http://webservice.jumbo.com/pda/}getInventoryRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryRequest", propOrder = {"authRequestHeader", "getInventoryRequestBody"})
public class GetInventoryRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8903605910408898568L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected GetInventoryRequestBody getInventoryRequestBody;

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
     * 获取getInventoryRequestBody属性的值。
     * 
     * @return possible object is {@link GetInventoryRequestBody }
     * 
     */
    public GetInventoryRequestBody getGetInventoryRequestBody() {
        return getInventoryRequestBody;
    }

    /**
     * 设置getInventoryRequestBody属性的值。
     * 
     * @param value allowed object is {@link GetInventoryRequestBody }
     * 
     */
    public void setGetInventoryRequestBody(GetInventoryRequestBody value) {
        this.getInventoryRequestBody = value;
    }

}
