
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadHandOverListRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadHandOverListRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lpcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transNos" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadHandOverListRequestBody", propOrder = {
    "uniqCode",
    "lpcode",
    "transNos"
})
public class UploadHandOverListRequestBody {

    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String lpcode;
    @XmlElement(nillable = true)
    protected List<String> transNos;

    /**
     * Gets the value of the uniqCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the lpcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpcode() {
        return lpcode;
    }

    /**
     * Sets the value of the lpcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpcode(String value) {
        this.lpcode = value;
    }

    /**
     * Gets the value of the transNos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transNos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransNos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTransNos() {
        if (transNos == null) {
            transNos = new ArrayList<String>();
        }
        return this.transNos;
    }

}
