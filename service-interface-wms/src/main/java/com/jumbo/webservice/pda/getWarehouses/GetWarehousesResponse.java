package com.jumbo.webservice.pda.getWarehouses;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getWarehousesResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getWarehousesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getWarehousesResponseBody" type="{http://webservice.jumbo.com/pda/}getWarehousesResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWarehousesResponse", propOrder = {"authResponseHeader", "getWarehousesResponseBody"})
public class GetWarehousesResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6654755960069129575L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetWarehousesResponseBody getWarehousesResponseBody;

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
     * 获取getWarehousesResponseBody属性的值。
     * 
     * @return possible object is {@link GetWarehousesResponseBody }
     * 
     */
    public GetWarehousesResponseBody getGetWarehousesResponseBody() {
        return getWarehousesResponseBody;
    }

    /**
     * 设置getWarehousesResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetWarehousesResponseBody }
     * 
     */
    public void setGetWarehousesResponseBody(GetWarehousesResponseBody value) {
        this.getWarehousesResponseBody = value;
    }

}
