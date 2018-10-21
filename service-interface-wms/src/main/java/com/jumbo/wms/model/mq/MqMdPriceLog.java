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
@Table(name = "T_MQ_MD_PRICE_LOG")
public class MqMdPriceLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 497294817652361978L;

    /**
     * ID
     */
    private Long id;

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


    private Date createTime;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 消息批次ID
     */
    private Long msgBatchId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MQMDPRICE", sequenceName = "S_T_MQ_MD_PRICE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MQMDPRICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SUPPLIER_SKU_CODE", length = 50)
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

    @Column(name = "MD_PRICE")
    public BigDecimal getMdPrice() {
        return mdPrice;
    }

    public void setMdPrice(BigDecimal mdPrice) {
        this.mdPrice = mdPrice;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

}
