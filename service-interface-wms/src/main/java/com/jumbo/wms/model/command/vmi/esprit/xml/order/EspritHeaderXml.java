package com.jumbo.wms.model.command.vmi.esprit.xml.order;

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
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}fromGLN"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}toGLN"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}fromNode"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}toNode"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}sequenceNumber"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}numberOfRecords"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}generationDate"/>
 *         &lt;element ref="{http://www.esprit.com.cn/XMLSchema/eShopInterface/espOrder.xsd}generationTime"/>
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
public class EspritHeaderXml implements Serializable {


    private static final long serialVersionUID = 2428431108881121834L;
    @XmlElement(required = true)
    public String fromGLN;
    @XmlElement(required = true)
    public String toGLN;
    @XmlElement(required = true)
    public String fromNode;
    @XmlElement(required = true)
    public String toNode;
    @XmlElement(required = true)
    public String sequenceNumber;
    @XmlElement(required = true)
    public String numberOfRecords;
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
     * @return possible object is {@link String }
     * 
     */
    public String getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSequenceNumber(String value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the numberOfRecords property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * Sets the value of the numberOfRecords property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNumberOfRecords(String value) {
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
