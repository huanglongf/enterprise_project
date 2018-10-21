package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * baseResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="baseResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseResponseBody", propOrder = {"status", "result"})
public class BaseResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5222782642182893633L;
    @XmlElement(required = true)
    protected String status;
    protected String result;

    /**
     * 获取status属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * 获取result属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setResult(String value) {
        this.result = value;
    }

}
