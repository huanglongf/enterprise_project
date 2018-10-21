package com.jumbo.webservice.pda.getTransNo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.TransNoDetail;


/**
 * <p>
 * Java class for getTransNoResponseBody complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTransNoResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transNoDetails" type="{http://webservice.jumbo.com/pda/}transNoDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTransNoResponseBody", propOrder = {"transNoDetails"})
public class GetTransNoResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3552130218010229747L;
    protected List<TransNoDetail> transNoDetails;

    /**
     * Gets the value of the transNoDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the transNoDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getTransNoDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TransNoDetail }
     * 
     * 
     */
    public List<TransNoDetail> getTransNoDetails() {
        if (transNoDetails == null) {
            transNoDetails = new ArrayList<TransNoDetail>();
        }
        return this.transNoDetails;
    }

}
