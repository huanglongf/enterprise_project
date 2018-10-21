package com.jumbo.wms.model.vmi.warehouse;

import java.io.Serializable;

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

/**
 * SF外包仓反馈SN号表
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_MSG_RTN_SN_DETAIL")
public class RtnSnDetail implements Serializable {


    private static final long serialVersionUID = -3338046490744724555L;
    /**
     * PK
     */
    private Long id;
    /**
     * 反馈SN号
     */
    private String sn;
    /**
     * 出库箱明细行ID
     */
    private MsgRtnOutboundContainerLine outLine;
    /**
     * 入库明细行ID
     */
    private MsgRtnInboundOrderLine inLine;

    /**
     * 商品编码
     */
    private String skuCode;

    /**
     * 出库ID
     */
    private MsgRtnOutbound out;

    private Boolean isSend;

    @Column(name = "SN")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_LINE_ID")
    @Index(name = "IDX_RTN_SN_OUTLINE")
    public MsgRtnOutboundContainerLine getOutLine() {
        return outLine;
    }

    public void setOutLine(MsgRtnOutboundContainerLine outLine) {
        this.outLine = outLine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IN_LINE_ID")
    @Index(name = "IDX_RTN_SN_INLINE")
    public MsgRtnInboundOrderLine getInLine() {
        return inLine;
    }

    public void setInLine(MsgRtnInboundOrderLine inLine) {
        this.inLine = inLine;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SN_DETAIL", sequenceName = "S_T_WH_MSG_RTN_SN_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SN_DETAIL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_ID")
    public MsgRtnOutbound getOut() {
        return out;
    }

    public void setOut(MsgRtnOutbound out) {
        this.out = out;
    }

    @Column(name = "IS_SEND")
    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }



}
