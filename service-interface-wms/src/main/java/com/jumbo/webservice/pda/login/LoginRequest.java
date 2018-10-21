package com.jumbo.webservice.pda.login;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.base.AuthRequest;
import com.jumbo.webservice.base.AuthRequestHeader;


/**
 * <p>
 * loginRequest complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="loginRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequestHeader" type="{http://webservice.jumbo.com/pda/}authRequestHeader"/>
 *         &lt;element name="loginRequestBody" type="{http://webservice.jumbo.com/pda/}loginRequestBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginRequest", propOrder = {"authRequestHeader", "loginRequestBody"})
public class LoginRequest implements AuthRequest, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1865920191261219503L;
    @XmlElement(required = true)
    protected AuthRequestHeader authRequestHeader;
    @XmlElement(required = true)
    protected LoginRequestBody loginRequestBody;

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
     * 获取loginRequestBody属性的值。
     * 
     * @return possible object is {@link LoginRequestBody }
     * 
     */
    public LoginRequestBody getLoginRequestBody() {
        return loginRequestBody;
    }

    /**
     * 设置loginRequestBody属性的值。
     * 
     * @param value allowed object is {@link LoginRequestBody }
     * 
     */
    public void setLoginRequestBody(LoginRequestBody value) {
        this.loginRequestBody = value;
    }

}
