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

@Entity
@Table(name = "T_WH_MSG_RTN_ADJUSTMENT_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnAdjustmentLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6057806245601715970L;

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
     * ids明细单号
     */
    private String idsLineId;

    private MsgRtnAdjustment adjustment;
    /**
     * 金宝贝条码
     */
    private String gbcode;

    private int version;

    /**
     * 过期时间
     */
    private Date expDate;
    /**
     * 备注
     */
    private String extMemo;

    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_RTN_ADJUSTMENT_LINE", sequenceName = "S_T_WH_MSG_RTN_ADJUSTMENT_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_RTN_ADJUSTMENT_LINE")
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
    @Index(name = "IDX_MSG_RALINE_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "IDS_LINE_ID")
    public String getIdsLineId() {
        return idsLineId;
    }

    public void setIdsLineId(String idsLineId) {
        this.idsLineId = idsLineId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADJUSTMENT_ID")
    @Index(name = "IDX_ADJUSTMENT_LINE_MSG")
    public MsgRtnAdjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(MsgRtnAdjustment adjustment) {
        this.adjustment = adjustment;
    }

    @Column(name = "GB_CODE", length = 100)
    public String getGbcode() {
        return gbcode;
    }

    public void setGbcode(String gbcode) {
        this.gbcode = gbcode;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }


}
