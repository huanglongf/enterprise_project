package com.jumbo.wms.model.mq;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_MQ_SKU_PRICE_LOG")
public class MqSkuPriceLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3169602351028473954L;

    /**
     * ID
     */
    private Long id;

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
     * 店铺id
     */
    private Long shopId;

    /**
     * 消息批次ID
     */
    private Long msgBatchId;

    /**
     * 操作类型： 1:新增 5:更新
     */
    private Integer opType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MQPRICE", sequenceName = "S_T_MQ_SKU_PRICE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MQPRICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "JM_CODE", length = 50)
    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    @Column(name = "SUPPLIER_CODE", length = 50)
    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    @Column(name = "EFF_DATE")
    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    @Column(name = "RETAIL_PRICE")
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "PRE_PRICE")
    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    @Column(name = "PRO_PRICE")
    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    @Column(name = "FOB")
    public BigDecimal getFob() {
        return fob;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "MSG_BATCH_ID")
    public Long getMsgBatchId() {
        return msgBatchId;
    }

    public void setMsgBatchId(Long msgBatchId) {
        this.msgBatchId = msgBatchId;
    }

    @Column(name = "OP_TYPE")
    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

}
