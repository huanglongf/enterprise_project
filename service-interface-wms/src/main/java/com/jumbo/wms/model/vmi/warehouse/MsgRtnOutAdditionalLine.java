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

/**
 * 外包仓出库耗材中间表
 * @author dly
 *
 */
@Entity
@Table(name = "T_WH_MSG_RTN_ADD_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnOutAdditionalLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6560973364834002481L;
    /**
     * PK
     */
    private Long id;
    /**
     * Sku条码，唯一标示Sku的编码
     */
    private String barCode;
    /**
     * sku 编码
     */
    private String skuCode;
    /**
     * 数量
     */
    public Long qty;
    
    public int version;

    /**
     * 相关T_WH_MSG_OUTBOUND
     */
    private MsgRtnOutbound msgRtnOutbound;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSGOLINE", sequenceName = "S_T_WH_MSG_RTN_ADD_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSGOLINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BAR_CODE", length = 100)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "SKU_CODE", length = 100)
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

    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msg_rtn_outbound_id")
    @Index(name = "IDX_MSG_RTN_OUTBOUND_LINE")
    public MsgRtnOutbound getMsgRtnOutbound() {
        return msgRtnOutbound;
    }

    public void setMsgRtnOutbound(MsgRtnOutbound msgRtnOutbound) {
        this.msgRtnOutbound = msgRtnOutbound;
    }

   

}
