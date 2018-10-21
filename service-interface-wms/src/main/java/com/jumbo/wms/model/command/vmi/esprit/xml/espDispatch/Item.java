package com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch;

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
 *       &lt;attribute name="year" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="2008"/>
 *             &lt;enumeration value="2009"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="styleShortDesc" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="CLEAN SNGL JSY POLO SS TEE"/>
 *             &lt;enumeration value="URB.TW WP BASIC FIT PNT 33"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="style" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="BG9554"/>
 *             &lt;enumeration value="TC1646M"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="sku" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="6907075037709"/>
 *             &lt;enumeration value="6908414206985"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="size" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="32"/>
 *             &lt;enumeration value="XS"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="season" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="B"/>
 *             &lt;enumeration value="T"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="qty" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *             &lt;enumeration value="4"/>
 *             &lt;enumeration value="8"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="division" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="SPT"/>
 *             &lt;enumeration value="WCA"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="colorDesc" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="NIGHT BLUE"/>
 *             &lt;enumeration value="WHITE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="color" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="100"/>
 *             &lt;enumeration value="409"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "item")
public class Item implements Serializable {


    private static final long serialVersionUID = -4494661726896969404L;
    @XmlAttribute(required = true)
    protected String year;
    @XmlAttribute(required = true)
    protected String styleShortDesc;
    @XmlAttribute(required = true)
    protected String style;
    @XmlAttribute(required = true)
    protected String sku;
    @XmlAttribute(required = true)
    protected String size;
    @XmlAttribute(required = true)
    protected String season;
    @XmlAttribute(required = true)
    protected long qty;
    @XmlAttribute(required = true)
    protected String division;
    @XmlAttribute(required = true)
    protected String colorDesc;
    @XmlAttribute(required = true)
    protected String color;

    /**
     * Gets the value of the year property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setYear(String value) {
        this.year = value;
    }

    /**
     * Gets the value of the styleShortDesc property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleShortDesc() {
        return styleShortDesc;
    }

    /**
     * Sets the value of the styleShortDesc property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleShortDesc(String value) {
        this.styleShortDesc = value;
    }

    /**
     * Gets the value of the style property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyle(String value) {
        this.style = value;
    }

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
     * Gets the value of the size property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * Gets the value of the season property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSeason() {
        return season;
    }

    /**
     * Sets the value of the season property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSeason(String value) {
        this.season = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     */
    public long getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     */
    public void setQty(long value) {
        this.qty = value;
    }

    /**
     * Gets the value of the division property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the value of the division property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDivision(String value) {
        this.division = value;
    }

    /**
     * Gets the value of the colorDesc property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getColorDesc() {
        return colorDesc;
    }

    /**
     * Sets the value of the colorDesc property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setColorDesc(String value) {
        this.colorDesc = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setColor(String value) {
        this.color = value;
    }

}
