package com.jumbo.webservice.pda.getReturnOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.ShelvesSkuDetial;
import com.jumbo.webservice.pda.Sku;


/**
 * <p>
 * getReturnOrderResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getReturnOrderResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="slipCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="returnOrderDetails" type="{http://webservice.jumbo.com/pda/}shelvesSkuDetial" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "getReturnOrderResponseBody", propOrder = {"uniqCode", "code", "slipCode", "returnOrderDetails", "skus"})
public class GetReturnOrderResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5085419503668060219L;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String slipCode;
    protected List<ShelvesSkuDetial> returnOrderDetails;
    protected List<Sku> skus;

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
     * 获取slipCode属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSlipCode() {
        return slipCode;
    }

    /**
     * 设置slipCode属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSlipCode(String value) {
        this.slipCode = value;
    }

    /**
     * Gets the value of the returnOrderDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the returnOrderDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getReturnOrderDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ShelvesSkuDetial }
     * 
     * 
     */
    public List<ShelvesSkuDetial> getReturnOrderDetails() {
        if (returnOrderDetails == null) {
            returnOrderDetails = new ArrayList<ShelvesSkuDetial>();
        }
        return this.returnOrderDetails;
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
