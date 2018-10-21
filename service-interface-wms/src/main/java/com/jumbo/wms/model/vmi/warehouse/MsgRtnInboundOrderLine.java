package com.jumbo.wms.model.vmi.warehouse;


import java.util.Date;

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


//
@Entity
@Table(name = "T_WH_MSG_RTN_INBOUND_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnInboundOrderLine extends BaseModel {

    private static final long serialVersionUID = -8474610533687546591L;

    private Long id;

    /**
     * 条码
     */
    private String barcode;


    /**
     * 编码
     */
    private String skuCode;

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
     * 返回库存状态
     */
    private String outStatus;

    /**
     * 相关入库单
     */
    private MsgRtnInboundOrder msgRtnInOrder;


    private String remark;


    private Long goodBase;

    private Long damagedBase;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 过期时间
     */
    private Date expDate;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_RTN_INBOUND_LINE", sequenceName = "S_T_WH_MSG_RTN_INBOUND_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_RTN_INBOUND_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BARCODE", length = 50)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "SKU_CODE", length = 50)
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

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_MSG_RILI_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSG_RTN_INORDER_ID")
    @Index(name = "IDX_MSG_RTN_INORDER_LINE_MSG")
    public MsgRtnInboundOrder getMsgRtnInOrder() {
        return msgRtnInOrder;
    }

    public void setMsgRtnInOrder(MsgRtnInboundOrder msgRtnInOrder) {
        this.msgRtnInOrder = msgRtnInOrder;
    }

    @Column(name = "OUT_STATUS")
    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "GOOD_BASE")
    public Long getGoodBase() {
        return goodBase;
    }

    public void setGoodBase(Long goodBase) {
        this.goodBase = goodBase;
    }

    @Column(name = "DAMAGED_BASE")
    public Long getDamagedBase() {
        return damagedBase;
    }

    public void setDamagedBase(Long damagedBase) {
        this.damagedBase = damagedBase;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }


}
