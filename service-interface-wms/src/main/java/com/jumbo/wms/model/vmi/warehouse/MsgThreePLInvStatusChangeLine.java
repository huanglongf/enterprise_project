package com.jumbo.wms.model.vmi.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 外包仓库存状态调整反馈
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_THREEPL_INVSTATUS_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgThreePLInvStatusChangeLine extends BaseModel {

    private static final long serialVersionUID = -6833351949249668767L;

    private Long id;

    /**
     * 商品对接唯一标识
     */
    private String skuCode;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 原库存状态
     */
    private String formStatus;

    /**
     * 新库存状态
     */
    private String toStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 行ID
     */
    private MsgThreePLInvStatusChange msgThreePLInv;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_THREEPL_INV_LINE", sequenceName = "S_THREEPL_INVSTATUS_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_THREEPL_INV_LINE")
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

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "FROM_STATUS")
    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    @Column(name = "TO_STATUS")
    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @ManyToOne
    @JoinColumn(name = "THREEPL_INV_ID")
    public MsgThreePLInvStatusChange getMsgThreePLInv() {
        return msgThreePLInv;
    }

    public void setMsgThreePLInv(MsgThreePLInvStatusChange msgThreePLInv) {
        this.msgThreePLInv = msgThreePLInv;
    }

}
