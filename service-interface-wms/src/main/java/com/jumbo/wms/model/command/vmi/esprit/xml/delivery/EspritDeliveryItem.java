package com.jumbo.wms.model.command.vmi.esprit.xml.delivery;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="sku" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="13"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="receivedQty" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"receivedQty", "sku"})
@XmlRootElement(name = "item")
public class EspritDeliveryItem implements Serializable {


    private static final long serialVersionUID = -2039269757413481963L;
    @XmlAttribute(required = true)
    public String sku;
    @XmlAttribute(required = true)
    public String receivedQty;

    /**
     * Gets the value of the sku property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSku() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSku(String value) {
        this.sku = value;
    }

    /**
     * Gets the value of the receivedQty property.
     * 
     */
    public String getReceivedQty() {
        return receivedQty;
    }

    /**
     * Sets the value of the receivedQty property.
     * 
     */
    public void setReceivedQty(String value) {
        this.receivedQty = value;
    }

}
