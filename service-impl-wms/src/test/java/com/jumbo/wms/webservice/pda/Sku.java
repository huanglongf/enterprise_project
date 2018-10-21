
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sku complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
@XmlType(name = "sku", propOrder = {
    "code",
    "barCodes",
    "skuName",
    "supplierCode",
    "keyProp",
    "color",
    "skuSize",
    "isSnSku",
    "isExpDateSku"
})
public class Sku {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(nillable = true)
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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the barCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the barCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBarCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
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
     * Gets the value of the skuName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * Sets the value of the skuName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuName(String value) {
        this.skuName = value;
    }

    /**
     * Gets the value of the supplierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * Sets the value of the supplierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierCode(String value) {
        this.supplierCode = value;
    }

    /**
     * Gets the value of the keyProp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyProp() {
        return keyProp;
    }

    /**
     * Sets the value of the keyProp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyProp(String value) {
        this.keyProp = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Gets the value of the skuSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuSize() {
        return skuSize;
    }

    /**
     * Sets the value of the skuSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuSize(String value) {
        this.skuSize = value;
    }

    /**
     * Gets the value of the isSnSku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSnSku() {
        return isSnSku;
    }

    /**
     * Sets the value of the isSnSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSnSku(String value) {
        this.isSnSku = value;
    }

    /**
     * Gets the value of the isExpDateSku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsExpDateSku() {
        return isExpDateSku;
    }

    /**
     * Sets the value of the isExpDateSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsExpDateSku(String value) {
        this.isExpDateSku = value;
    }

}
