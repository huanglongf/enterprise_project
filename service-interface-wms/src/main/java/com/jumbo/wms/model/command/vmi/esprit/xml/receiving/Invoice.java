package com.jumbo.wms.model.command.vmi.esprit.xml.receiving;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}currency"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}totalQty"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}totalFOB"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}totalQTP"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}dutyFee"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}freightFee" minOccurs="0"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd}customerAgentFee" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceNumber" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="EMCOLWN338449"/>
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
@XmlType(name = "", propOrder = {"currency", "totalQty", "totalFOB", "totalGTP"})
@XmlRootElement(name = "invoice")
public class Invoice implements Serializable {


    private static final long serialVersionUID = 816481913137093473L;
    @XmlElement(required = true)
    public String currency;
    @XmlElement(required = true)
    public String totalQty;
    @XmlElement(required = true)
    public String totalFOB;
    @XmlElement(required = true)
    public String totalGTP;
    @XmlAttribute(required = true)
    public String invoiceNumber;

    /**
     * Gets the value of the currency property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the totalQty property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTotalQty() {
        return totalQty;
    }

    /**
     * Sets the value of the totalQty property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTotalQty(String value) {
        this.totalQty = value;
    }

    /**
     * Gets the value of the totalFOB property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTotalFOB() {
        return totalFOB;
    }

    /**
     * Sets the value of the totalFOB property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTotalFOB(String value) {
        this.totalFOB = value;
    }


    /**
     * Gets the value of the invoiceNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    public String getTotalGTP() {
        return totalGTP;
    }

    public void setTotalGTP(String totalGTP) {
        this.totalGTP = totalGTP;
    }


}
