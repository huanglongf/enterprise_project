package com.jumbo.webservice.pda.uploadPickingOutbound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.PickingOutboundDetail;


/**
 * <p>
 * uploadPickingOutboundRequestBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="uploadPickingOutboundRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pickingOutboundDetails" type="{http://webservice.jumbo.com/pda/}pickingOutboundDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadPickingOutboundRequestBody", propOrder = {"code", "transNo", "pickingOutboundDetails"})
public class UploadPickingOutboundRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4697924856287801302L;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String transNo;
    protected List<PickingOutboundDetail> pickingOutboundDetails;

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
     * 获取transNo属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * 设置transNo属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTransNo(String value) {
        this.transNo = value;
    }

    /**
     * Gets the value of the pickingOutboundDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the pickingOutboundDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getPickingOutboundDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PickingOutboundDetail }
     * 
     * 
     */
    public List<PickingOutboundDetail> getPickingOutboundDetails() {
        if (pickingOutboundDetails == null) {
            pickingOutboundDetails = new ArrayList<PickingOutboundDetail>();
        }
        return this.pickingOutboundDetails;
    }

}
