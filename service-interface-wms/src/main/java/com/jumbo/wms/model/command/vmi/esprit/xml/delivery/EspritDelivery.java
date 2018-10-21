package com.jumbo.wms.model.command.vmi.esprit.xml.delivery;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveryNo"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveryDate"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveryType"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveryStatus"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}goodsReceiptDate"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveredFromGLN" minOccurs="0"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveredToGLN" minOccurs="0"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}items"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"deliveryNo", "deliveryDate", "deliveryType", "deliveryStatus", "goodsReceiptDate", "deliveredFromGLN", "deliveredToGLN", "items"})
@XmlRootElement(name = "delivery")
public class EspritDelivery implements Serializable {


    private static final long serialVersionUID = -7346644506800972183L;
    @XmlElement(required = true)
    public String deliveryNo;
    @XmlElement(required = true)
    public String deliveryDate;
    @XmlElement(required = true)
    public String deliveryType;
    @XmlElement(required = true)
    public String deliveryStatus;
    @XmlElement(required = true)
    public String goodsReceiptDate;
    @XmlElement(required = true)
    public String deliveredFromGLN;
    @XmlElement(required = true)
    public String deliveredToGLN;
    @XmlElement(required = true)
    public EspritDeliveryItems items;

    /**
     * Gets the value of the deliveryNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * Sets the value of the deliveryNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveryNo(String value) {
        this.deliveryNo = value;
    }

    /**
     * Gets the value of the deliveryDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Sets the value of the deliveryDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    /**
     * Gets the value of the deliveryType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * Sets the value of the deliveryType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveryType(String value) {
        this.deliveryType = value;
    }

    /**
     * Gets the value of the deliveryStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * Sets the value of the deliveryStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveryStatus(String value) {
        this.deliveryStatus = value;
    }

    /**
     * Gets the value of the goodsReceiptDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGoodsReceiptDate() {
        return goodsReceiptDate;
    }

    /**
     * Sets the value of the goodsReceiptDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setGoodsReceiptDate(String value) {
        this.goodsReceiptDate = value;
    }

    /**
     * Gets the value of the deliveredFromGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveredFromGLN() {
        return deliveredFromGLN;
    }

    /**
     * Sets the value of the deliveredFromGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveredFromGLN(String value) {
        this.deliveredFromGLN = value;
    }

    /**
     * Gets the value of the deliveredToGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeliveredToGLN() {
        return deliveredToGLN;
    }

    /**
     * Sets the value of the deliveredToGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDeliveredToGLN(String value) {
        this.deliveredToGLN = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return possible object is {@link EspritDeliveryItems }
     * 
     */
    public EspritDeliveryItems getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value allowed object is {@link EspritDeliveryItems }
     * 
     */
    public void setItems(EspritDeliveryItems value) {
        this.items = value;
    }

}
