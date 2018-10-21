
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInventoryCheckRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInventoryCheckRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inventoryCheckDetails" type="{http://webservice.jumbo.com/pda/}inventory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInventoryCheckRequestBody", propOrder = {
    "code",
    "uniqCode",
    "inventoryCheckDetails"
})
public class UploadInventoryCheckRequestBody {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(nillable = true)
    protected List<Inventory> inventoryCheckDetails;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

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
     * Gets the value of the inventoryCheckDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inventoryCheckDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInventoryCheckDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Inventory }
     * 
     * 
     */
    public List<Inventory> getInventoryCheckDetails() {
        if (inventoryCheckDetails == null) {
            inventoryCheckDetails = new ArrayList<Inventory>();
        }
        return this.inventoryCheckDetails;
    }

}
