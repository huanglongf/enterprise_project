package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * pickingDatadetial complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="pickingDatadetial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="skuCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pgindex" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ext1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pickingDatadetial", propOrder = {"location", "skuCode", "qty", "pgindex", "ext1", "ext2", "ext3", "ext4", "ext5"})
public class PickingDatadetial implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4719555099555302441L;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected String skuCode;
    @XmlElement(required = true)
    protected String qty;
    @XmlElement(required = true)
    protected String pgindex;
    protected String ext1;
    protected String ext2;
    protected String ext3;
    protected String ext4;
    protected String ext5;

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

    /**
     * 获取skuCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * 设置skuCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuCode(String value) {
        this.skuCode = value;
    }

    /**
     * 获取qty属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getQty() {
        return qty;
    }

    /**
     * 设置qty属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setQty(String value) {
        this.qty = value;
    }

    /**
     * 获取pgindex属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPgindex() {
        return pgindex;
    }

    /**
     * 设置pgindex属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPgindex(String value) {
        this.pgindex = value;
    }

    /**
     * Gets the value of the ext1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * Sets the value of the ext1 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt1(String value) {
        this.ext1 = value;
    }

    /**
     * Gets the value of the ext2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * Sets the value of the ext2 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt2(String value) {
        this.ext2 = value;
    }

    /**
     * Gets the value of the ext3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * Sets the value of the ext3 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt3(String value) {
        this.ext3 = value;
    }

    /**
     * Gets the value of the ext4 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * Sets the value of the ext4 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt4(String value) {
        this.ext4 = value;
    }

    /**
     * Gets the value of the ext5 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * Sets the value of the ext5 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt5(String value) {
        this.ext5 = value;
    }
}
