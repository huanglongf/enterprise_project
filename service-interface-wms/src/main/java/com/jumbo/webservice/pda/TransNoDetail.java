package com.jumbo.webservice.pda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



/**
 * <p>
 * Java class for transNoDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transNoDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lpcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transNoDetailLines" type="{http://webservice.jumbo.com/pda/}transNoDetailLine" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transNoDetail", propOrder = {"lpcode", "uniqCode", "transNoDetailLines"})
public class TransNoDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7781175032737454431L;
    @XmlElement(required = true)
    protected String lpcode;
    @XmlElement(required = true)
    protected String uniqCode;
    protected List<TransNoDetailLine> transNoDetailLines;

    /**
     * Gets the value of the lpcode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLpcode() {
        return lpcode;
    }

    /**
     * Sets the value of the lpcode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLpcode(String value) {
        this.lpcode = value;
    }

    /**
     * Gets the value of the uniqCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the transNoDetailLines property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the transNoDetailLines property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getTransNoDetailLines().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TransNoDetailLine }
     * 
     * 
     */
    public List<TransNoDetailLine> getTransNoDetailLines() {
        if (transNoDetailLines == null) {
            transNoDetailLines = new ArrayList<TransNoDetailLine>();
        }
        return this.transNoDetailLines;
    }

}
