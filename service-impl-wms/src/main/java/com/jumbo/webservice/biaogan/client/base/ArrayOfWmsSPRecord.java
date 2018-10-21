package com.jumbo.webservice.biaogan.client.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for ArrayOfWmsSPRecord complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWmsSPRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WmsSPRecord" type="{http://orderstatusclient.warehouse.webservices.chamayi.chamago.com}WmsSPRecord" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWmsSPRecord", namespace = "http://orderstatusclient.warehouse.webservices.chamayi.chamago.com", propOrder = {"wmsSPRecord"})
public class ArrayOfWmsSPRecord {

    @XmlElement(name = "WmsSPRecord", nillable = true)
    protected List<WmsSPRecord> wmsSPRecord;

    /**
     * Gets the value of the wmsSPRecord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the wmsSPRecord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getWmsSPRecord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link WmsSPRecord }
     * 
     * 
     */
    public List<WmsSPRecord> getWmsSPRecord() {
        if (wmsSPRecord == null) {
            wmsSPRecord = new ArrayList<WmsSPRecord>();
        }
        return this.wmsSPRecord;
    }

}
