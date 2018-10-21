package com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery;

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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveredFromGLN"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveredToGLN"/>
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
public class Delivery implements Serializable {



    private static final long serialVersionUID = 887290630830923114L;
    @XmlElement(required = true)
    protected String deliveryNo;
    @XmlElement(required = true)
    protected String deliveryDate;
    @XmlElement(required = true)
    protected String deliveryType;
    @XmlElement(required = true)
    protected String deliveryStatus;
    @XmlElement(required = true)
    protected String goodsReceiptDate;
    @XmlElement(required = true)
    protected String deliveredFromGLN;
    @XmlElement(required = true)
    protected String deliveredToGLN;
    @XmlElement(required = true)
    protected Items items;

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
     * @return possible object is {@link Items }
     * 
     */
    public Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value allowed object is {@link Items }
     * 
     */
    public void setItems(Items value) {
        this.items = value;
    }

}
