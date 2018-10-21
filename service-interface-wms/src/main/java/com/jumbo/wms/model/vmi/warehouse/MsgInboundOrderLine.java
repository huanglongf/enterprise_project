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


@Entity
@Table(name = "T_WH_MSG_INBOUND_ORDER_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgInboundOrderLine extends BaseModel {

    private static final long serialVersionUID = 6881955603524063203L;

    private Long id;


    /**
     * 编码
     */
    private Sku sku;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 相关库存状态
     */
    private InventoryStatus invStatus;


    /**
     * 相关入库单
     */
    private MsgInboundOrder msgInOrder;
    
    /**
     * 实际退货单价
     */
    private BigDecimal unitPrice;
    
    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_INBOUND_ORDER_LINE", sequenceName = "S_T_WH_MSG_INBOUND_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_INBOUND_ORDER_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_MSGI_LINE_SKU")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSG_INORDER_ID")
    @Index(name = "IDX_MSG_INORDER_LINE_MSG")
    public MsgInboundOrder getMsgInOrder() {
        return msgInOrder;
    }

    public void setMsgInOrder(MsgInboundOrder msgInOrder) {
        this.msgInOrder = msgInOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_MSG_IN_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "UNIT_PRICE", precision = 10, scale = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
