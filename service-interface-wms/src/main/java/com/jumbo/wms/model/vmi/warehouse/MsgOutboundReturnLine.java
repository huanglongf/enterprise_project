package com.jumbo.wms.model.vmi.warehouse;

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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

// T_WH_MSG_OUTBOUND_ORDER_LINE
@Entity
@Table(name = "T_WH_MSG_OUTBOUND_RTN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOutboundReturnLine extends BaseModel {


    private static final long serialVersionUID = 5887468679359145133L;

    private Long id;

    private Sku sku;

    private Long qty = 0L;

    private BigDecimal unitPrice = new BigDecimal(0);

    private MsgOutboundReturn msgOrder;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_OUTBOUND_RTN_LINE", sequenceName = "S_T_WH_MSG_OUTBOUND_RTN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_OUTBOUND_RTN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_MSGO_RA_LINE_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "QUANTITY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSG_OUTORDER_ID")
    @Index(name = "IDX_MSG_OUT_RA_LINE_MSG")
    public MsgOutboundReturn getMsgOrder() {
        return msgOrder;
    }

    public void setMsgOrder(MsgOutboundReturn msgOrder) {
        this.msgOrder = msgOrder;
    }

    @Column(name = "UNIT_PRICE")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }


}
