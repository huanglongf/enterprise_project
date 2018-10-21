package com.jumbo.wms.model.vmi.warehouse;

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
import com.jumbo.wms.model.warehouse.InventoryStatus;

@Entity
@Table(name = "T_WH_MSG_RTN_RETURN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnReturnLine extends BaseModel {

    private static final long serialVersionUID = -8474610533687546591L;

    private Long id;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 商品
     */
    private Long skuId;

    /**
     * 库存状态
     */
    private InventoryStatus invStatus;

    /**
     * 相关入库单
     */
    private MsgRtnReturn msgRtnInOrder;

    private int version;

    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_RTN_RETURN_LINE", sequenceName = "S_T_WH_MSG_RTN_RETURN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_RTN_RETURN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_MSG_RALI_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETURN_ID")
    @Index(name = "IDX_RETURN_LINE_MSG")
    public MsgRtnReturn getMsgRtnInOrder() {
        return msgRtnInOrder;
    }

    public void setMsgRtnInOrder(MsgRtnReturn msgRtnInOrder) {
        this.msgRtnInOrder = msgRtnInOrder;
    }
}
