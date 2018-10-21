package com.jumbo.webservice.pda.getPickingData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthResponse;
import com.jumbo.webservice.base.AuthResponseHeader;


/**
 * <p>
 * getPickingDataResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getPickingDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authResponseHeader" type="{http://webservice.jumbo.com/pda/}authResponseHeader"/>
 *         &lt;element name="getPickingDataResponseBody" type="{http://webservice.jumbo.com/pda/}getPickingDataResponseBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPickingDataResponse", propOrder = {"authResponseHeader", "getPickingDataResponseBody"})
public class GetPickingDataResponse implements AuthResponse, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8077162704788619465L;
    @XmlElement(required = true)
    protected AuthResponseHeader authResponseHeader;
    @XmlElement(required = true)
    protected GetPickingDataResponseBody getPickingDataResponseBody;

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
     * 获取getPickingDataResponseBody属性的值。
     * 
     * @return possible object is {@link GetPickingDataResponseBody }
     * 
     */
    public GetPickingDataResponseBody getGetPickingDataResponseBody() {
        return getPickingDataResponseBody;
    }

    /**
     * 设置getPickingDataResponseBody属性的值。
     * 
     * @param value allowed object is {@link GetPickingDataResponseBody }
     * 
     */
    public void setGetPickingDataResponseBody(GetPickingDataResponseBody value) {
        this.getPickingDataResponseBody = value;
    }

}
