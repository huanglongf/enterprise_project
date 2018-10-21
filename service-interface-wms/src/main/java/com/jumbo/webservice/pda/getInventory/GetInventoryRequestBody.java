package com.jumbo.webservice.pda.getInventory;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * getInventoryRequestBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="skuBarCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryRequestBody", propOrder = {"uniqCode", "skuBarCode", "location"})
public class GetInventoryRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2621170818979078804L;
    @XmlElement(required = true)
    protected String uniqCode;
    protected String skuBarCode;
    protected String location;

    /**
     * 获取uniqCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * 设置uniqCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * 获取skuBarCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuBarCode() {
        return skuBarCode;
    }

    /**
     * 设置skuBarCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuBarCode(String value) {
        this.skuBarCode = value;
    }

    /**
     * 获取location属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置location属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLocation(String value) {
        this.location = value;
    }

}
