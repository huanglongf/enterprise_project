
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPickingDataResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPickingDataResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pickingDatadetials" type="{http://webservice.jumbo.com/pda/}pickingDatadetial" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skus" type="{http://webservice.jumbo.com/pda/}sku" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPickingDataResponseBody", propOrder = {
    "type",
    "code",
    "pickingDatadetials",
    "skus"
})
public class GetPickingDataResponseBody {

    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(nillable = true)
    protected List<PickingDatadetial> pickingDatadetials;
    @XmlElement(nillable = true)
    protected List<Sku> skus;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

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
     * Gets the value of the pickingDatadetials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pickingDatadetials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPickingDatadetials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PickingDatadetial }
     * 
     * 
     */
    public List<PickingDatadetial> getPickingDatadetials() {
        if (pickingDatadetials == null) {
            pickingDatadetials = new ArrayList<PickingDatadetial>();
        }
        return this.pickingDatadetials;
    }

    /**
     * Gets the value of the skus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sku }
     * 
     * 
     */
    public List<Sku> getSkus() {
        if (skus == null) {
            skus = new ArrayList<Sku>();
        }
        return this.skus;
    }

}
