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
import com.jumbo.wms.model.warehouse.InventoryStatus;

// T_WH_MSG_OUTBOUND_ORDER_LINE
@Entity
@Table(name = "T_WH_MSG_OUTBOUND_ORDER_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOutboundOrderLine extends BaseModel {

    private static final long serialVersionUID = 5887468679359145133L;

    private Long id;

    private Sku sku;

    private Long qty = 0L;
    /**
     * 活动
     */
    private String activitySource;

    private BigDecimal unitPrice = new BigDecimal(0);

    private MsgOutboundOrder msgOrder;

    private String lineReserve1;

    private BigDecimal totalActual = new BigDecimal(0);

    private String skuName;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    private InventoryStatus invStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_STA_LINE_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_OUTBOUND_ORDER_LINE", sequenceName = "S_T_WH_MSG_OUTBOUND_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_OUTBOUND_ORDER_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_MSGO_LINE_SKU")
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
    @Index(name = "IDX_MSG_OUTORDER_LINE_MSG")
    public MsgOutboundOrder getMsgOrder() {
        return msgOrder;
    }

    public void setMsgOrder(MsgOutboundOrder msgOrder) {
        this.msgOrder = msgOrder;
    }

    @Column(name = "UNIT_PRICE")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "LINE_RESERVE1", length = 500)
    public String getLineReserve1() {
        return lineReserve1;
    }

    public void setLineReserve1(String lineReserve1) {
        this.lineReserve1 = lineReserve1;
    }

    @Column(name = "TOTAL_ACTUAL")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "ACTIVITY_SOURCE", length = 30)
    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    @Column(name = "SKU_NAME")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

}
