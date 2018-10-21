package com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch;

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
 *         &lt;element ref="{}fromNode"/>
 *         &lt;element ref="{}toNode"/>
 *         &lt;element ref="{}fromGLN"/>
 *         &lt;element ref="{}toGLN"/>
 *         &lt;element ref="{}batchNo"/>
 *         &lt;element ref="{}noOfRecord"/>
 *         &lt;element ref="{}generationDate"/>
 *         &lt;element ref="{}generationTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"fromNode", "toNode", "fromGLN", "toGLN", "batchNo", "noOfRecord", "generationDate", "generationTime"})
@XmlRootElement(name = "header")
public class Header implements Serializable {


    private static final long serialVersionUID = -5683345011894304493L;
    @XmlElement(required = true)
    protected String fromNode;
    @XmlElement(required = true)
    protected String toNode;
    @XmlElement(required = true)
    protected String fromGLN;
    @XmlElement(required = true)
    protected String toGLN;
    @XmlElement(required = true)
    protected String batchNo;
    @XmlElement(required = true)
    protected String noOfRecord;
    @XmlElement(required = true)
    protected String generationDate;
    @XmlElement(required = true)
    protected String generationTime;

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
     * Gets the value of the batchNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * Sets the value of the batchNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBatchNo(String value) {
        this.batchNo = value;
    }

    /**
     * Gets the value of the noOfRecord property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNoOfRecord() {
        return noOfRecord;
    }

    /**
     * Sets the value of the noOfRecord property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNoOfRecord(String value) {
        this.noOfRecord = value;
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
