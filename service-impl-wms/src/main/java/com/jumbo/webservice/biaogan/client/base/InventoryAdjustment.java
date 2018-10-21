package com.jumbo.webservice.biaogan.client.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for InventoryAdjustment complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryAdjustment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adjNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cycleCountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fmqty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt06" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt07" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt08" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt09" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotatt12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toqty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wareHouseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryAdjustment", namespace = "http://bean.server.webservices.chamayi.chamago.com", propOrder = {"adjNo", "customerId", "cycleCountNo", "fmqty", "lotatt01", "lotatt02", "lotatt03", "lotatt04", "lotatt05", "lotatt06", "lotatt07",
        "lotatt08", "lotatt09", "lotatt10", "lotatt11", "lotatt12", "note", "qty", "reason", "reasonCode", "sku", "time", "toqty", "type", "wareHouseId"})
public class InventoryAdjustment {

    @XmlElementRef(name = "adjNo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> adjNo;
    @XmlElementRef(name = "customerId", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> customerId;
    @XmlElementRef(name = "cycleCountNo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> cycleCountNo;
    @XmlElementRef(name = "fmqty", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> fmqty;
    @XmlElementRef(name = "lotatt01", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt01;
    @XmlElementRef(name = "lotatt02", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt02;
    @XmlElementRef(name = "lotatt03", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt03;
    @XmlElementRef(name = "lotatt04", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt04;
    @XmlElementRef(name = "lotatt05", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt05;
    @XmlElementRef(name = "lotatt06", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt06;
    @XmlElementRef(name = "lotatt07", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt07;
    @XmlElementRef(name = "lotatt08", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt08;
    @XmlElementRef(name = "lotatt09", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt09;
    @XmlElementRef(name = "lotatt10", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt10;
    @XmlElementRef(name = "lotatt11", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt11;
    @XmlElementRef(name = "lotatt12", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> lotatt12;
    @XmlElementRef(name = "note", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> note;
    @XmlElementRef(name = "qty", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> qty;
    @XmlElementRef(name = "reason", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> reason;
    @XmlElementRef(name = "reasonCode", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> reasonCode;
    @XmlElementRef(name = "sku", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> sku;
    @XmlElementRef(name = "time", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> time;
    @XmlElementRef(name = "toqty", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> toqty;
    @XmlElementRef(name = "type", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> type;
    @XmlElementRef(name = "wareHouseId", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> wareHouseId;

    /**
     * Gets the value of the adjNo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAdjNo() {
        return adjNo;
    }

    /**
     * Sets the value of the adjNo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAdjNo(JAXBElement<String> value) {
        this.adjNo = value;
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
     * Gets the value of the cycleCountNo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCycleCountNo() {
        return cycleCountNo;
    }

    /**
     * Sets the value of the cycleCountNo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCycleCountNo(JAXBElement<String> value) {
        this.cycleCountNo = value;
    }

    /**
     * Gets the value of the fmqty property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFmqty() {
        return fmqty;
    }

    /**
     * Sets the value of the fmqty property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFmqty(JAXBElement<String> value) {
        this.fmqty = value;
    }

    /**
     * Gets the value of the lotatt01 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt01() {
        return lotatt01;
    }

    /**
     * Sets the value of the lotatt01 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt01(JAXBElement<String> value) {
        this.lotatt01 = value;
    }

    /**
     * Gets the value of the lotatt02 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt02() {
        return lotatt02;
    }

    /**
     * Sets the value of the lotatt02 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt02(JAXBElement<String> value) {
        this.lotatt02 = value;
    }

    /**
     * Gets the value of the lotatt03 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt03() {
        return lotatt03;
    }

    /**
     * Sets the value of the lotatt03 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt03(JAXBElement<String> value) {
        this.lotatt03 = value;
    }

    /**
     * Gets the value of the lotatt04 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt04() {
        return lotatt04;
    }

    /**
     * Sets the value of the lotatt04 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt04(JAXBElement<String> value) {
        this.lotatt04 = value;
    }

    /**
     * Gets the value of the lotatt05 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt05() {
        return lotatt05;
    }

    /**
     * Sets the value of the lotatt05 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt05(JAXBElement<String> value) {
        this.lotatt05 = value;
    }

    /**
     * Gets the value of the lotatt06 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt06() {
        return lotatt06;
    }

    /**
     * Sets the value of the lotatt06 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt06(JAXBElement<String> value) {
        this.lotatt06 = value;
    }

    /**
     * Gets the value of the lotatt07 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt07() {
        return lotatt07;
    }

    /**
     * Sets the value of the lotatt07 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt07(JAXBElement<String> value) {
        this.lotatt07 = value;
    }

    /**
     * Gets the value of the lotatt08 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt08() {
        return lotatt08;
    }

    /**
     * Sets the value of the lotatt08 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt08(JAXBElement<String> value) {
        this.lotatt08 = value;
    }

    /**
     * Gets the value of the lotatt09 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt09() {
        return lotatt09;
    }

    /**
     * Sets the value of the lotatt09 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt09(JAXBElement<String> value) {
        this.lotatt09 = value;
    }

    /**
     * Gets the value of the lotatt10 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt10() {
        return lotatt10;
    }

    /**
     * Sets the value of the lotatt10 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt10(JAXBElement<String> value) {
        this.lotatt10 = value;
    }

    /**
     * Gets the value of the lotatt11 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt11() {
        return lotatt11;
    }

    /**
     * Sets the value of the lotatt11 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt11(JAXBElement<String> value) {
        this.lotatt11 = value;
    }

    /**
     * Gets the value of the lotatt12 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLotatt12() {
        return lotatt12;
    }

    /**
     * Sets the value of the lotatt12 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLotatt12(JAXBElement<String> value) {
        this.lotatt12 = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setNote(JAXBElement<String> value) {
        this.note = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setQty(JAXBElement<String> value) {
        this.qty = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReason(JAXBElement<String> value) {
        this.reason = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReasonCode(JAXBElement<String> value) {
        this.reasonCode = value;
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

    /**
     * Gets the value of the time property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setTime(JAXBElement<String> value) {
        this.time = value;
    }

    /**
     * Gets the value of the toqty property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getToqty() {
        return toqty;
    }

    /**
     * Sets the value of the toqty property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setToqty(JAXBElement<String> value) {
        this.toqty = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setType(JAXBElement<String> value) {
        this.type = value;
    }

    /**
     * Gets the value of the wareHouseId property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getWareHouseId() {
        return wareHouseId;
    }

    /**
     * Sets the value of the wareHouseId property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setWareHouseId(JAXBElement<String> value) {
        this.wareHouseId = value;
    }

}
