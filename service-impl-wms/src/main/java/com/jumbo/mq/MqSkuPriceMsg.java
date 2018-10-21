package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MqSkuPriceMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6335887111419025725L;

    /**
     * oms商品编码
     */
    private String jmCode;

    /**
     * 供应商商品编码
     */
    private String supplierSkuCode;

    /**
     * 生效日期
     */
    private Date effDate;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 预售价
     */
    private BigDecimal prePrice;

    /**
     * 活动价
     */
    private BigDecimal proPrice;

    /**
     * 成本价
     */
    private BigDecimal fob;

    /**
     * 操作类型： 1:新增 5:更新
     */
    private Integer opType;

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    public BigDecimal getFob() {
        return fob;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

}
