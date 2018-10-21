package com.jumbo.webservice.base;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * authRequestHeader complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="authRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signSalt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sequenceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authRequestHeader", propOrder = {"organizationCode", "signSalt", "sign", "sequenceCode", "callTime"})
public class AuthRequestHeader implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6423696503843533493L;
    @XmlElement(required = true)
    protected String organizationCode;
    @XmlElement(required = true)
    protected String signSalt;
    @XmlElement(required = true)
    protected String sign;
    @XmlElement(required = true)
    protected String sequenceCode;
    @XmlElement(required = true)
    protected String callTime;

    /**
     * 获取organizationCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * 设置organizationCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOrganizationCode(String value) {
        this.organizationCode = value;
    }

    /**
     * 获取signSalt属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSignSalt() {
        return signSalt;
    }

    /**
     * 设置signSalt属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSignSalt(String value) {
        this.signSalt = value;
    }

    /**
     * 获取sign属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置sign属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSign(String value) {
        this.sign = value;
    }

    /**
     * 获取sequenceCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSequenceCode() {
        return sequenceCode;
    }

    /**
     * 设置sequenceCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSequenceCode(String value) {
        this.sequenceCode = value;
    }

    /**
     * 获取callTime属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCallTime() {
        return callTime;
    }

    /**
     * 设置callTime属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCallTime(String value) {
        this.callTime = value;
    }

}
