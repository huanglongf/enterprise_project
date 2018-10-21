package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * inventory complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="inventory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skuBarCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ocpQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "inventory", propOrder = {"skuBarCode", "location", "qty", "invStatus", "ocpQty", "ext1", "ext2", "ext3", "ext4", "ext5"})
public class Inventory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -316818058325412727L;
    @XmlElement(required = true)
    protected String skuBarCode;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected String qty;
    @XmlElement(required = true)
    protected String invStatus;
    protected String ocpQty;
    protected String ext1;
    protected String ext2;
    protected String ext3;
    protected String ext4;
    protected String ext5;

    /**
     * Gets the value of the skuBarCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuBarCode() {
        return skuBarCode;
    }

    /**
     * Sets the value of the skuBarCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuBarCode(String value) {
        this.skuBarCode = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setQty(String value) {
        this.qty = value;
    }

    /**
     * Gets the value of the invStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInvStatus() {
        return invStatus;
    }

    /**
     * Sets the value of the invStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setInvStatus(String value) {
        this.invStatus = value;
    }

    /**
     * Gets the value of the ocpQty property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOcpQty() {
        return ocpQty;
    }

    /**
     * Sets the value of the ocpQty property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOcpQty(String value) {
        this.ocpQty = value;
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
