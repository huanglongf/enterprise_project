package com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery;

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
 *             &lt;enumeration value="A6900371109675"/>
 *             &lt;enumeration value="A6900371110596"/>
 *             &lt;enumeration value="A6900371119841"/>
 *             &lt;enumeration value="A6900371130037"/>
 *             &lt;enumeration value="A6905524463611"/>
 *             &lt;enumeration value="A6905524541616"/>
 *             &lt;enumeration value="A6905524568811"/>
 *             &lt;enumeration value="A6905524729175"/>
 *             &lt;enumeration value="A6905524781883"/>
 *             &lt;enumeration value="A6905524811115"/>
 *             &lt;enumeration value="A6905524814499"/>
 *             &lt;enumeration value="A6905524833926"/>
 *             &lt;enumeration value="A6905524842256"/>
 *             &lt;enumeration value="A6905524845660"/>
 *             &lt;enumeration value="A6905524887851"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="receivedQty" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *             &lt;enumeration value="1"/>
 *             &lt;enumeration value="1000000000"/>
 *             &lt;enumeration value="2"/>
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



    private static final long serialVersionUID = 4081119961821688973L;
    @XmlAttribute(required = true)
    protected String sku;
    @XmlAttribute(required = true)
    protected long receivedQty;

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
    public long getReceivedQty() {
        return receivedQty;
    }

    /**
     * Sets the value of the receivedQty property.
     * 
     */
    public void setReceivedQty(long value) {
        this.receivedQty = value;
    }

}
