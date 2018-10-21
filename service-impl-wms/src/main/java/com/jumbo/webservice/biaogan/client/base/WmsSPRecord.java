package com.jumbo.webservice.biaogan.client.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for WmsSPRecord complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WmsSPRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FIELD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIELD9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WmsSPRecord", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", propOrder = {"field1", "field10", "field11", "field12", "field13", "field14", "field15", "field16", "field17", "field18", "field19",
        "field2", "field20", "field21", "field22", "field23", "field24", "field25", "field26", "field27", "field28", "field29", "field3", "field30", "field4", "field5", "field6", "field7", "field8", "field9"})
public class WmsSPRecord {

    @XmlElementRef(name = "FIELD1", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field1;
    @XmlElementRef(name = "FIELD10", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field10;
    @XmlElementRef(name = "FIELD11", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field11;
    @XmlElementRef(name = "FIELD12", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field12;
    @XmlElementRef(name = "FIELD13", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field13;
    @XmlElementRef(name = "FIELD14", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field14;
    @XmlElementRef(name = "FIELD15", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field15;
    @XmlElementRef(name = "FIELD16", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field16;
    @XmlElementRef(name = "FIELD17", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field17;
    @XmlElementRef(name = "FIELD18", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field18;
    @XmlElementRef(name = "FIELD19", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field19;
    @XmlElementRef(name = "FIELD2", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field2;
    @XmlElementRef(name = "FIELD20", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field20;
    @XmlElementRef(name = "FIELD21", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field21;
    @XmlElementRef(name = "FIELD22", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field22;
    @XmlElementRef(name = "FIELD23", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field23;
    @XmlElementRef(name = "FIELD24", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field24;
    @XmlElementRef(name = "FIELD25", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field25;
    @XmlElementRef(name = "FIELD26", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field26;
    @XmlElementRef(name = "FIELD27", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field27;
    @XmlElementRef(name = "FIELD28", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field28;
    @XmlElementRef(name = "FIELD29", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field29;
    @XmlElementRef(name = "FIELD3", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field3;
    @XmlElementRef(name = "FIELD30", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field30;
    @XmlElementRef(name = "FIELD4", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field4;
    @XmlElementRef(name = "FIELD5", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field5;
    @XmlElementRef(name = "FIELD6", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field6;
    @XmlElementRef(name = "FIELD7", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field7;
    @XmlElementRef(name = "FIELD8", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field8;
    @XmlElementRef(name = "FIELD9", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> field9;

    /**
     * Gets the value of the field1 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD1() {
        return field1;
    }

    /**
     * Sets the value of the field1 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD1(JAXBElement<String> value) {
        this.field1 = value;
    }

    /**
     * Gets the value of the field10 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD10() {
        return field10;
    }

    /**
     * Sets the value of the field10 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD10(JAXBElement<String> value) {
        this.field10 = value;
    }

    /**
     * Gets the value of the field11 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD11() {
        return field11;
    }

    /**
     * Sets the value of the field11 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD11(JAXBElement<String> value) {
        this.field11 = value;
    }

    /**
     * Gets the value of the field12 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD12() {
        return field12;
    }

    /**
     * Sets the value of the field12 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD12(JAXBElement<String> value) {
        this.field12 = value;
    }

    /**
     * Gets the value of the field13 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD13() {
        return field13;
    }

    /**
     * Sets the value of the field13 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD13(JAXBElement<String> value) {
        this.field13 = value;
    }

    /**
     * Gets the value of the field14 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD14() {
        return field14;
    }

    /**
     * Sets the value of the field14 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD14(JAXBElement<String> value) {
        this.field14 = value;
    }

    /**
     * Gets the value of the field15 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD15() {
        return field15;
    }

    /**
     * Sets the value of the field15 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD15(JAXBElement<String> value) {
        this.field15 = value;
    }

    /**
     * Gets the value of the field16 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD16() {
        return field16;
    }

    /**
     * Sets the value of the field16 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD16(JAXBElement<String> value) {
        this.field16 = value;
    }

    /**
     * Gets the value of the field17 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD17() {
        return field17;
    }

    /**
     * Sets the value of the field17 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD17(JAXBElement<String> value) {
        this.field17 = value;
    }

    /**
     * Gets the value of the field18 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD18() {
        return field18;
    }

    /**
     * Sets the value of the field18 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD18(JAXBElement<String> value) {
        this.field18 = value;
    }

    /**
     * Gets the value of the field19 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD19() {
        return field19;
    }

    /**
     * Sets the value of the field19 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD19(JAXBElement<String> value) {
        this.field19 = value;
    }

    /**
     * Gets the value of the field2 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD2() {
        return field2;
    }

    /**
     * Sets the value of the field2 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD2(JAXBElement<String> value) {
        this.field2 = value;
    }

    /**
     * Gets the value of the field20 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD20() {
        return field20;
    }

    /**
     * Sets the value of the field20 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD20(JAXBElement<String> value) {
        this.field20 = value;
    }

    /**
     * Gets the value of the field21 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD21() {
        return field21;
    }

    /**
     * Sets the value of the field21 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD21(JAXBElement<String> value) {
        this.field21 = value;
    }

    /**
     * Gets the value of the field22 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD22() {
        return field22;
    }

    /**
     * Sets the value of the field22 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD22(JAXBElement<String> value) {
        this.field22 = value;
    }

    /**
     * Gets the value of the field23 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD23() {
        return field23;
    }

    /**
     * Sets the value of the field23 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD23(JAXBElement<String> value) {
        this.field23 = value;
    }

    /**
     * Gets the value of the field24 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD24() {
        return field24;
    }

    /**
     * Sets the value of the field24 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD24(JAXBElement<String> value) {
        this.field24 = value;
    }

    /**
     * Gets the value of the field25 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD25() {
        return field25;
    }

    /**
     * Sets the value of the field25 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD25(JAXBElement<String> value) {
        this.field25 = value;
    }

    /**
     * Gets the value of the field26 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD26() {
        return field26;
    }

    /**
     * Sets the value of the field26 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD26(JAXBElement<String> value) {
        this.field26 = value;
    }

    /**
     * Gets the value of the field27 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD27() {
        return field27;
    }

    /**
     * Sets the value of the field27 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD27(JAXBElement<String> value) {
        this.field27 = value;
    }

    /**
     * Gets the value of the field28 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD28() {
        return field28;
    }

    /**
     * Sets the value of the field28 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD28(JAXBElement<String> value) {
        this.field28 = value;
    }

    /**
     * Gets the value of the field29 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD29() {
        return field29;
    }

    /**
     * Sets the value of the field29 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD29(JAXBElement<String> value) {
        this.field29 = value;
    }

    /**
     * Gets the value of the field3 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD3() {
        return field3;
    }

    /**
     * Sets the value of the field3 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD3(JAXBElement<String> value) {
        this.field3 = value;
    }

    /**
     * Gets the value of the field30 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD30() {
        return field30;
    }

    /**
     * Sets the value of the field30 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD30(JAXBElement<String> value) {
        this.field30 = value;
    }

    /**
     * Gets the value of the field4 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD4() {
        return field4;
    }

    /**
     * Sets the value of the field4 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD4(JAXBElement<String> value) {
        this.field4 = value;
    }

    /**
     * Gets the value of the field5 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD5() {
        return field5;
    }

    /**
     * Sets the value of the field5 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD5(JAXBElement<String> value) {
        this.field5 = value;
    }

    /**
     * Gets the value of the field6 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD6() {
        return field6;
    }

    /**
     * Sets the value of the field6 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD6(JAXBElement<String> value) {
        this.field6 = value;
    }

    /**
     * Gets the value of the field7 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD7() {
        return field7;
    }

    /**
     * Sets the value of the field7 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD7(JAXBElement<String> value) {
        this.field7 = value;
    }

    /**
     * Gets the value of the field8 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD8() {
        return field8;
    }

    /**
     * Sets the value of the field8 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD8(JAXBElement<String> value) {
        this.field8 = value;
    }

    /**
     * Gets the value of the field9 property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getFIELD9() {
        return field9;
    }

    /**
     * Sets the value of the field9 property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setFIELD9(JAXBElement<String> value) {
        this.field9 = value;
    }

}
