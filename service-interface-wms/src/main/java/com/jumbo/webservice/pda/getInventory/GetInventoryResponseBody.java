package com.jumbo.webservice.pda.getInventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.Inventory;


/**
 * <p>
 * getInventoryResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inventorys" type="{http://webservice.jumbo.com/pda/}inventory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryResponseBody", propOrder = {"inventorys"})
public class GetInventoryResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 484627595274546784L;
    protected List<Inventory> inventorys;

    /**
     * Gets the value of the inventorys property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the inventorys property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getInventorys().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Inventory }
     * 
     * 
     */
    public List<Inventory> getInventorys() {
        if (inventorys == null) {
            inventorys = new ArrayList<Inventory>();
        }
        return this.inventorys;
    }

}
