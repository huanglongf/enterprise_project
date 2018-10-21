package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MqMdPriceMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7250714009897780534L;

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
    private BigDecimal mdPrice;

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

    public BigDecimal getMdPrice() {
        return mdPrice;
    }

    public void setMdPrice(BigDecimal mdPrice) {
        this.mdPrice = mdPrice;
    }

}
