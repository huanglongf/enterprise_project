package com.jumbo.webservice.pda.getInventoryCheck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * getInventoryCheckResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInventoryCheckResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locations" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInventoryCheckResponseBody", propOrder = {"code", "locations"})
public class GetInventoryCheckResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8612375516686855365L;
    @XmlElement(required = true)
    protected String code;
    protected List<String> locations;

    /**
     * 获取code属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the locations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the locations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getLocations() {
        if (locations == null) {
            locations = new ArrayList<String>();
        }
        return this.locations;
    }

}
