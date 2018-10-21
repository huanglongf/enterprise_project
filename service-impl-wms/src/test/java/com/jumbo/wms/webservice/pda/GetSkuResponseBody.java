
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSkuResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSkuResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skuId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="skuname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuResponseBody", propOrder = {
    "skuId",
    "barcode",
    "skuname"
})
public class GetSkuResponseBody {

    protected long skuId;
    @XmlElement(required = true)
    protected String barcode;
    @XmlElement(required = true)
    protected String skuname;

    /**
     * Gets the value of the skuId property.
     * 
     */
    public long getSkuId() {
        return skuId;
    }

    /**
     * Sets the value of the skuId property.
     * 
     */
    public void setSkuId(long value) {
        this.skuId = value;
    }

    /**
     * Gets the value of the barcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the value of the barcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcode(String value) {
        this.barcode = value;
    }

    /**
     * Gets the value of the skuname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuname() {
        return skuname;
    }

    /**
     * Sets the value of the skuname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuname(String value) {
        this.skuname = value;
    }

}
