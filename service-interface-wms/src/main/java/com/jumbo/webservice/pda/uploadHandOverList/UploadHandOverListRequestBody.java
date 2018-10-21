package com.jumbo.webservice.pda.uploadHandOverList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * uploadHandOverListRequestBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
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
@XmlType(name = "uploadHandOverListRequestBody", propOrder = {"uniqCode", "lpcode", "transNos"})
public class UploadHandOverListRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2229214829732140782L;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String lpcode;
    protected List<String> transNos;

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
     * Gets the value of the lpcode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLpcode() {
        return lpcode;
    }

    /**
     * Sets the value of the lpcode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLpcode(String value) {
        this.lpcode = value;
    }

    /**
     * Gets the value of the transNos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the transNos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getTransNos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
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
