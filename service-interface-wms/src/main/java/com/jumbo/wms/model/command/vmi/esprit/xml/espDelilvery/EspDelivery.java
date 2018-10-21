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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}header"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}deliveries"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"header", "deliveries"})
@XmlRootElement(name = "espDelivery", namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd")
public class EspDelivery implements Serializable {


    private static final long serialVersionUID = 1007349977820883997L;
    @XmlElement(required = true)
    protected Header header;
    @XmlElement(required = true)
    protected Deliveries deliveries;

    /**
     * Gets the value of the header property.
     * 
     * @return possible object is {@link Header }
     * 
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value allowed object is {@link Header }
     * 
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the deliveries property.
     * 
     * @return possible object is {@link Deliveries }
     * 
     */
    public Deliveries getDeliveries() {
        return deliveries;
    }

    /**
     * Sets the value of the deliveries property.
     * 
     * @param value allowed object is {@link Deliveries }
     * 
     */
    public void setDeliveries(Deliveries value) {
        this.deliveries = value;
    }

}
