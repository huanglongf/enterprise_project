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
@Table(name = "T_WH_MSG_RTN_OUTBOUND_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnOutboundLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4508614948253691465L;
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
    private MsgRtnOutbound msgOutbound;

    /**
     * 库存状态
     */
    private InventoryStatus invStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 过期时间格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private Date expDate;

    /**
     * 箱号
     */
    private String cartonNo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSGOLINE", sequenceName = "S_T_WH_MSG_RTN_OUTBOUND_LINE", allocationSize = 1)
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
    @JoinColumn(name = "MSG_OUTBOUND_ID")
    @Index(name = "IDX_MSG_OUTBOUND_LINE")
    public MsgRtnOutbound getMsgOutbound() {
        return msgOutbound;
    }

    public void setMsgOutbound(MsgRtnOutbound msgOutbound) {
        this.msgOutbound = msgOutbound;
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

    @Column(name = "CARTON_NO")
    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

}
