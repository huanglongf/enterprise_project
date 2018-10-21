package com.jumbo.webservice.biaogan.client.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for CmerpInputFromBg complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CmerpInputFromBg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="asnlineno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnreference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnreference2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnreference3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnreference4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asnreference5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asntype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expectedQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isIntact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivedQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivedtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="warehouseid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CmerpInputFromBg", namespace = "http://bean.server.webservices.chamayi.chamago.com", propOrder = {"asnlineno", "asnno", "asnreference", "asnreference2", "asnreference3", "asnreference4", "asnreference5", "asntype", "customerid",
        "expectedQty", "isIntact", "lotatt01", "lotatt02", "lotatt03", "lotatt04", "lotatt05", "lotatt06", "lotatt07", "lotatt08", "lotatt09", "lotatt10", "lotatt11", "lotatt12", "notes", "receivedQty", "receivedtime", "sku", "status", "tid",
        "warehouseid"})
public class CmerpInputFromBg {

    @XmlElementRef(name = "asnlineno", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnlineno;
    @XmlElementRef(name = "asnno", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnno;
    @XmlElementRef(name = "asnreference", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnreference;
    @XmlElementRef(name = "asnreference2", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnreference2;
    @XmlElementRef(name = "asnreference3", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnreference3;
    @XmlElementRef(name = "asnreference4", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnreference4;
    @XmlElementRef(name = "asnreference5", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asnreference5;
    @XmlElementRef(name = "asntype", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> asntype;
    @XmlElementRef(name = "customerid", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> customerid;
    @XmlElementRef(name = "expectedQty", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> expectedQty;
    @XmlElementRef(name = "isIntact", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> isIntact;
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
    @XmlElementRef(name = "notes", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> notes;
    @XmlElementRef(name = "receivedQty", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> receivedQty;
    @XmlElementRef(name = "receivedtime", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> receivedtime;
    @XmlElementRef(name = "sku", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> sku;
    @XmlElementRef(name = "status", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> status;
    @XmlElementRef(name = "tid", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> tid;
    @XmlElementRef(name = "warehouseid", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> warehouseid;

    /**
     * Gets the value of the asnlineno property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnlineno() {
        return asnlineno;
    }

    /**
     * Sets the value of the asnlineno property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnlineno(JAXBElement<String> value) {
        this.asnlineno = value;
    }

    /**
     * Gets the value of the asnno property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnno() {
        return asnno;
    }

    /**
     * Sets the value of the asnno property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnno(JAXBElement<String> value) {
        this.asnno = value;
    }

    /**
     * Gets the value of the asnreference property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnreference() {
        return asnreference;
    }

    /**
     * Sets the value of the asnreference property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnreference(JAXBElement<String> value) {
        this.asnreference = value;
    }

    /**
     * Gets the value of the asnreference2 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnreference2() {
        return asnreference2;
    }

    /**
     * Sets the value of the asnreference2 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnreference2(JAXBElement<String> value) {
        this.asnreference2 = value;
    }

    /**
     * Gets the value of the asnreference3 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnreference3() {
        return asnreference3;
    }

    /**
     * Sets the value of the asnreference3 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnreference3(JAXBElement<String> value) {
        this.asnreference3 = value;
    }

    /**
     * Gets the value of the asnreference4 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnreference4() {
        return asnreference4;
    }

    /**
     * Sets the value of the asnreference4 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnreference4(JAXBElement<String> value) {
        this.asnreference4 = value;
    }

    /**
     * Gets the value of the asnreference5 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsnreference5() {
        return asnreference5;
    }

    /**
     * Sets the value of the asnreference5 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsnreference5(JAXBElement<String> value) {
        this.asnreference5 = value;
    }

    /**
     * Gets the value of the asntype property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAsntype() {
        return asntype;
    }

    /**
     * Sets the value of the asntype property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAsntype(JAXBElement<String> value) {
        this.asntype = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCustomerid() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCustomerid(JAXBElement<String> value) {
        this.customerid = value;
    }

    /**
     * Gets the value of the expectedQty property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getExpectedQty() {
        return expectedQty;
    }

    /**
     * Sets the value of the expectedQty property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setExpectedQty(JAXBElement<String> value) {
        this.expectedQty = value;
    }

    /**
     * Gets the value of the isIntact property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getIsIntact() {
        return isIntact;
    }

    /**
     * Sets the value of the isIntact property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setIsIntact(JAXBElement<String> value) {
        this.isIntact = value;
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
     * Gets the value of the notes property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setNotes(JAXBElement<String> value) {
        this.notes = value;
    }

    /**
     * Gets the value of the receivedQty property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReceivedQty() {
        return receivedQty;
    }

    /**
     * Sets the value of the receivedQty property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReceivedQty(JAXBElement<String> value) {
        this.receivedQty = value;
    }

    /**
     * Gets the value of the receivedtime property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReceivedtime() {
        return receivedtime;
    }

    /**
     * Sets the value of the receivedtime property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReceivedtime(JAXBElement<String> value) {
        this.receivedtime = value;
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
     * Gets the value of the status property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = value;
    }

    /**
     * Gets the value of the tid property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setTid(JAXBElement<String> value) {
        this.tid = value;
    }

    /**
     * Gets the value of the warehouseid property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getWarehouseid() {
        return warehouseid;
    }

    /**
     * Sets the value of the warehouseid property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setWarehouseid(JAXBElement<String> value) {
        this.warehouseid = value;
    }

}
