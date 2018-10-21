package com.jumbo.webservice.biaogan.client.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for IdxSoTransaction complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdxSoTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carrierID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carrierName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdxSoTransaction", namespace = "http://bean.server.webservices.chamayi.chamago.com", propOrder = {"bgNo", "carrierID", "carrierName", "customerId", "flag", "num", "orderNo", "shipNo", "sku"})
public class IdxSoTransaction {

    @XmlElementRef(name = "bgNo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> bgNo;
    @XmlElementRef(name = "carrierID", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> carrierID;
    @XmlElementRef(name = "carrierName", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> carrierName;
    @XmlElementRef(name = "customerId", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> customerId;
    @XmlElementRef(name = "flag", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> flag;
    @XmlElementRef(name = "num", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> num;
    @XmlElementRef(name = "orderNo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> orderNo;
    @XmlElementRef(name = "shipNo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> shipNo;
    @XmlElementRef(name = "sku", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> sku;

    /**
     * Gets the value of the bgNo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getBgNo() {
        return bgNo;
    }

    /**
     * Sets the value of the bgNo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setBgNo(JAXBElement<String> value) {
        this.bgNo = value;
    }

    /**
     * Gets the value of the carrierID property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCarrierID() {
        return carrierID;
    }

    /**
     * Sets the value of the carrierID property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCarrierID(JAXBElement<String> value) {
        this.carrierID = value;
    }

    /**
     * Gets the value of the carrierName property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCarrierName() {
        return carrierName;
    }

    /**
     * Sets the value of the carrierName property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCarrierName(JAXBElement<String> value) {
        this.carrierName = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCustomerId(JAXBElement<String> value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFlag(JAXBElement<String> value) {
        this.flag = value;
    }

    /**
     * Gets the value of the num property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getNum() {
        return num;
    }

    /**
     * Sets the value of the num property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setNum(JAXBElement<String> value) {
        this.num = value;
    }

    /**
     * Gets the value of the orderNo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the value of the orderNo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setOrderNo(JAXBElement<String> value) {
        this.orderNo = value;
    }

    /**
     * Gets the value of the shipNo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getShipNo() {
        return shipNo;
    }

    /**
     * Sets the value of the shipNo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setShipNo(JAXBElement<String> value) {
        this.shipNo = value;
    }

    /**
     * Gets the value of the sku property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getSku() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setSku(JAXBElement<String> value) {
        this.sku = value;
    }

}
