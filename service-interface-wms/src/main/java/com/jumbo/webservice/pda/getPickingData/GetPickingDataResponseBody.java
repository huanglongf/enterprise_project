package com.jumbo.webservice.pda.getPickingData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.PickingDatadetial;
import com.jumbo.webservice.pda.Sku;


/**
 * <p>
 * getPickingDataResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
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
@XmlType(name = "getPickingDataResponseBody", propOrder = {"type", "code", "pickingDatadetials", "skus"})
public class GetPickingDataResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3351861665280372220L;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String code;
    protected List<PickingDatadetial> pickingDatadetials;
    protected List<Sku> skus;

    /**
     * 获取type属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setType(String value) {
        this.type = value;
    }

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
     * Gets the value of the pickingDatadetials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the pickingDatadetials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getPickingDatadetials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PickingDatadetial }
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
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the skus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getSkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Sku }
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
