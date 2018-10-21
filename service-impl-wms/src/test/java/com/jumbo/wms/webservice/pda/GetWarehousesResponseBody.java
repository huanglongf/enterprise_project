
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getWarehousesResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getWarehousesResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="warehouses" type="{http://webservice.jumbo.com/pda/}warehouse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWarehousesResponseBody", propOrder = {
    "warehouses"
})
public class GetWarehousesResponseBody {

    @XmlElement(nillable = true)
    protected List<Warehouse> warehouses;

    /**
     * Gets the value of the warehouses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warehouses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarehouses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Warehouse }
     * 
     * 
     */
    public List<Warehouse> getWarehouses() {
        if (warehouses == null) {
            warehouses = new ArrayList<Warehouse>();
        }
        return this.warehouses;
    }

}
