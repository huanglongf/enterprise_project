package com.jumbo.webservice.sf;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for logisticQueryStandard complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="logisticQueryStandard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logisticQueryStandard", propOrder = {"arg0"})
public class LogisticQueryStandard implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7582536116142824068L;
    protected String arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setArg0(String value) {
        this.arg0 = value;
    }

}
