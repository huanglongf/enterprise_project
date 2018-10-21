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
 * warehouse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="warehouse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="logicalWarehouses" type="{http://webservice.jumbo.com/pda/}logicalWarehouse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "warehouse", propOrder = {"name", "logicalWarehouses"})
public class Warehouse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3636008679124774943L;
    @XmlElement(required = true)
    protected String name;
    protected List<LogicalWarehouse> logicalWarehouses;

    /**
     * 获取name属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the logicalWarehouses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the logicalWarehouses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getLogicalWarehouses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link LogicalWarehouse }
     * 
     * 
     */
    public List<LogicalWarehouse> getLogicalWarehouses() {
        if (logicalWarehouses == null) {
            logicalWarehouses = new ArrayList<LogicalWarehouse>();
        }
        return this.logicalWarehouses;
    }

}
