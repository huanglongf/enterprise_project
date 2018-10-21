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
 * Java class for shelvesSkuDetial complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shelvesSkuDetial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="skuCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locations" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sns" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ext1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ext5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shelvesSkuDetial", propOrder = {"skuCode", "qty", "locations", "mDate", "invStatus", "index", "sns", "ext1", "ext2", "ext3", "ext4", "ext5"})
public class ShelvesSkuDetial implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8685693580548504688L;
    @XmlElement(required = true)
    protected String skuCode;
    @XmlElement(required = true)
    protected String qty;
    protected List<String> locations;
    protected String mDate;
    protected String invStatus;
    protected String index;
    protected List<String> sns;
    protected String ext1;
    protected String ext2;
    protected String ext3;
    protected String ext4;
    protected String ext5;

    /**
     * Gets the value of the skuCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * Sets the value of the skuCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSkuCode(String value) {
        this.skuCode = value;
    }

    /**
     * Gets the value of the qty property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getQty() {
        return qty;
    }

    /**
     * Sets the value of the qty property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setQty(String value) {
        this.qty = value;
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

    /**
     * Gets the value of the mDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMDate() {
        return mDate;
    }

    /**
     * Sets the value of the mDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMDate(String value) {
        this.mDate = value;
    }

    /**
     * Gets the value of the invStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInvStatus() {
        return invStatus;
    }

    /**
     * Sets the value of the invStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setInvStatus(String value) {
        this.invStatus = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setIndex(String value) {
        this.index = value;
    }

    /**
     * Gets the value of the sns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the sns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     *    getSns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getSns() {
        if (sns == null) {
            sns = new ArrayList<String>();
        }
        return this.sns;
    }

    /**
     * Gets the value of the ext1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * Sets the value of the ext1 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt1(String value) {
        this.ext1 = value;
    }

    /**
     * Gets the value of the ext2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * Sets the value of the ext2 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt2(String value) {
        this.ext2 = value;
    }

    /**
     * Gets the value of the ext3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * Sets the value of the ext3 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt3(String value) {
        this.ext3 = value;
    }

    /**
     * Gets the value of the ext4 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * Sets the value of the ext4 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt4(String value) {
        this.ext4 = value;
    }

    /**
     * Gets the value of the ext5 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * Sets the value of the ext5 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setExt5(String value) {
        this.ext5 = value;
    }

}
