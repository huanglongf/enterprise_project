package com.jumbo.wms.model.command.vmi.esprit.xml.delivery;

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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}fromGLN"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}toGLN"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}fromNode"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}toNode"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}sequenceNumber"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}numberOfRecords"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}generationDate"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd}generationTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"fromGLN", "toGLN", "fromNode", "toNode", "sequenceNumber", "numberOfRecords", "generationDate", "generationTime"})
@XmlRootElement(name = "header")
public class EspritDeliveryHeader implements Serializable {


    private static final long serialVersionUID = 1737158365781064631L;
    @XmlElement(required = true)
    public String fromGLN = "4046655000664";
    @XmlElement(required = true)
    public String toGLN = "4046655000480";
    @XmlElement(required = true)
    public String fromNode = "CREDREWW";
    @XmlElement(required = true)
    public String toNode = "CREDR";
    public String sequenceNumber;
    public int numberOfRecords;
    @XmlElement(required = true)
    public String generationDate;
    @XmlElement(required = true)
    public String generationTime;

    /**
     * Gets the value of the fromGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromGLN() {
        return fromGLN;
    }

    /**
     * Sets the value of the fromGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFromGLN(String value) {
        this.fromGLN = value;
    }

    /**
     * Gets the value of the toGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToGLN() {
        return toGLN;
    }

    /**
     * Sets the value of the toGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setToGLN(String value) {
        this.toGLN = value;
    }

    /**
     * Gets the value of the fromNode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromNode() {
        return fromNode;
    }

    /**
     * Sets the value of the fromNode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFromNode(String value) {
        this.fromNode = value;
    }

    /**
     * Gets the value of the toNode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToNode() {
        return toNode;
    }

    /**
     * Sets the value of the toNode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setToNode(String value) {
        this.toNode = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     */
    public String getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(String value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the numberOfRecords property.
     * 
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * Sets the value of the numberOfRecords property.
     * 
     */
    public void setNumberOfRecords(int value) {
        this.numberOfRecords = value;
    }

    /**
     * Gets the value of the generationDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGenerationDate() {
        return generationDate;
    }

    /**
     * Sets the value of the generationDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setGenerationDate(String value) {
        this.generationDate = value;
    }

    /**
     * Gets the value of the generationTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGenerationTime() {
        return generationTime;
    }

    /**
     * Sets the value of the generationTime property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setGenerationTime(String value) {
        this.generationTime = value;
    }

}
