
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadPickingOutboundRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
@XmlType(name = "uploadPickingOutboundRequestBody", propOrder = {
    "code",
    "transNo",
    "pickingOutboundDetails"
})
public class UploadPickingOutboundRequestBody {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String transNo;
    @XmlElement(nillable = true)
    protected List<PickingOutboundDetail> pickingOutboundDetails;

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
     * Gets the value of the transNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * Sets the value of the transNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransNo(String value) {
        this.transNo = value;
    }

    /**
     * Gets the value of the pickingOutboundDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pickingOutboundDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPickingOutboundDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PickingOutboundDetail }
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
