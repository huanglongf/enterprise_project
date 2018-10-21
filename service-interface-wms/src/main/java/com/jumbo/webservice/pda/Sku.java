package com.jumbo.webservice.pda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * sku complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="sku">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="barCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skuName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyProp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="skuSize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isSnSku" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isExpDateSku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sku", propOrder = {"code", "barCodes", "skuName", "supplierCode", "keyProp", "color", "skuSize", "isSnSku", "isExpDateSku"})
public class Sku implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4231775887753178342L;
    @XmlElement(required = true)
    protected String code;
    protected List<String> barCodes;
    @XmlElement(required = true)
    protected String skuName;
    @XmlElement(required = true)
    protected String supplierCode;
    protected String keyProp;
    protected String color;
    protected String skuSize;
    @XmlElement(required = true)
    protected String isSnSku;
    protected String isExpDateSku;

    /**
     * 获取code属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the barCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the barCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getBarCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getBarCodes() {
        if (barCodes == null) {
            barCodes = new ArrayList<String>();
        }
        return this.barCodes;
    }

    /**
     * 获取skuName属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置skuName属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuName(String value) {
        this.skuName = value;
    }

    /**
     * 获取supplierCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * 设置supplierCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSupplierCode(String value) {
        this.supplierCode = value;
    }

    /**
     * 获取keyProp属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getKeyProp() {
        return keyProp;
    }

    /**
     * 设置keyProp属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setKeyProp(String value) {
        this.keyProp = value;
    }

    /**
     * 获取color属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置color属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * 获取skuSize属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuSize() {
        return skuSize;
    }

    /**
     * 设置skuSize属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuSize(String value) {
        this.skuSize = value;
    }

    /**
     * 获取isSnSku属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIsSnSku() {
        return isSnSku;
    }

    /**
     * 设置isSnSku属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setIsSnSku(String value) {
        this.isSnSku = value;
    }

    /**
     * 获取isExpDateSku属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIsExpDateSku() {
        return isExpDateSku;
    }

    /**
     * 设置isExpDateSku属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setIsExpDateSku(String value) {
        this.isExpDateSku = value;
    }

}
