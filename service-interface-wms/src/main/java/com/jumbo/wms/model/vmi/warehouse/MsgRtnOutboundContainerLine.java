package com.jumbo.wms.model.vmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 顺丰外包仓出库箱明细
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_RTN_CONTAINER_LINE")
public class MsgRtnOutboundContainerLine implements Serializable {

    private static final long serialVersionUID = -3687455747842770706L;
    /**
     * PK　ID
     */
    private Long id;
    /**
     * 箱号
     */
    private String containerId;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 商品Id
     */
    private Sku sku;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 数量单位
     */
    private String qtyUm;
    /**
     * 批号
     */
    private String batchCode;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 重量单位
     */
    private String weightUm;
    /**
     * 包装员
     */
    private String userStamp;
    /**
     * 出库反馈单
     */
    private MsgRtnOutbound msgRtnOutbound;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_RTN_OUT_CONTAINER", sequenceName = "S_T_RTN_CONTAINER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RTN_OUT_CONTAINER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CONTAINER_ID", length = 50)
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_RTN_CONTAINER_SKUID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "QTY_UM")
    public String getQtyUm() {
        return qtyUm;
    }

    public void setQtyUm(String qtyUm) {
        this.qtyUm = qtyUm;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "WEIGHT_UM")
    public String getWeightUm() {
        return weightUm;
    }

    public void setWeightUm(String weightUm) {
        this.weightUm = weightUm;
    }

    @Column(name = "USER_STAMP")
    public String getUserStamp() {
        return userStamp;
    }

    public void setUserStamp(String userStamp) {
        this.userStamp = userStamp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSG_RTN_OUTBOUND_ID")
    @Index(name = "IDX_RTN_OUTBOUND_ID")
    public MsgRtnOutbound getMsgRtnOutbound() {
        return msgRtnOutbound;
    }

    public void setMsgRtnOutbound(MsgRtnOutbound msgRtnOutbound) {
        this.msgRtnOutbound = msgRtnOutbound;
    }

}
