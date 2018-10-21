package com.jumbo.webservice.pda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * logicalWarehouse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="logicalWarehouse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logicalWarehouse", propOrder = {"uniqCode", "name"})
public class LogicalWarehouse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3016128041293514016L;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String name;

    /**
     * 获取uniqCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * 设置uniqCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

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

}
