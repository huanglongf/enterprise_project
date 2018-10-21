
package com.jumbo.wms.webservice.pda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadInboundOnShelvesRequestBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadInboundOnShelvesRequestBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniqCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inboundOnShelvesDetials" type="{http://webservice.jumbo.com/pda/}shelvesSkuDetial" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadInboundOnShelvesRequestBody", propOrder = {
    "code",
    "uniqCode",
    "userName",
    "inboundOnShelvesDetials"
})
public class UploadInboundOnShelvesRequestBody {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String uniqCode;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(nillable = true)
    protected List<ShelvesSkuDetial> inboundOnShelvesDetials;

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
     * Gets the value of the uniqCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqCode() {
        return uniqCode;
    }

    /**
     * Sets the value of the uniqCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqCode(String value) {
        this.uniqCode = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the inboundOnShelvesDetials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inboundOnShelvesDetials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInboundOnShelvesDetials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShelvesSkuDetial }
     * 
     * 
     */
    public List<ShelvesSkuDetial> getInboundOnShelvesDetials() {
        if (inboundOnShelvesDetials == null) {
            inboundOnShelvesDetials = new ArrayList<ShelvesSkuDetial>();
        }
        return this.inboundOnShelvesDetials;
    }

}
