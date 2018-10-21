package com.jumbo.webservice.pda.login;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * loginRequestBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="loginRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginRequestBody", propOrder = {"user", "password"})
public class LoginRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3440804797906270819L;
    @XmlElement(required = true)
    protected String user;
    @XmlElement(required = true)
    protected String password;

    /**
     * 获取user属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置user属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * 获取password属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
