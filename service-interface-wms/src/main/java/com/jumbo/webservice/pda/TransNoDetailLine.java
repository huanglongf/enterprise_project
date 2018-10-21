package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for transNoDetailLine complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transNoDetailLine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transNos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiver" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transNoDetailLine", propOrder = {"transNos", "receiver", "weight"})
public class TransNoDetailLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8884291409182654159L;
    @XmlElement(required = true)
    protected String transNos;
    @XmlElement(required = true)
    protected String receiver;
    @XmlElement(required = true)
    protected String weight;

    /**
     * Gets the value of the transNos property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransNos() {
        return transNos;
    }

    /**
     * Sets the value of the transNos property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTransNos(String value) {
        this.transNos = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setReceiver(String value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setWeight(String value) {
        this.weight = value;
    }

}
