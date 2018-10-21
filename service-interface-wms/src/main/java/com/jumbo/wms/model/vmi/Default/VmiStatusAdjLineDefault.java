package com.jumbo.wms.model.vmi.Default;

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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_STA_ADJ_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiStatusAdjLineDefault extends BaseModel {

    private static final long serialVersionUID = -3068435456190277328L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品唯一编码t_bi_inv_sku.ext_code2
     */
    private String upc;

    /**
     * 数量t_wh_sta_line.quantity
     */
    private Long qty;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * t_sta_adj外键
     */
    private VmiStatusAdjDefault adjId;

    /**
     * 版本
     */
    private int version;

    /**
     * 宝尊系统的初始库存状态
     */
    private String fromStatus;

    /**
     * 宝尊系统调整后的库存状态
     */
    private String toStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_ADJ_LINE", sequenceName = "S_T_STA_ADJ_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_ADJ_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADJ_ID")
    @Index(name = "INDEX_T_STA_ADJ_LINE_ADJ")
    public VmiStatusAdjDefault getAdjId() {
        return adjId;
    }

    public void setAdjId(VmiStatusAdjDefault adjId) {
        this.adjId = adjId;
    }

    @Column(name = "FROM_STATUS")
    public String getFromStatus() {
        return fromStatus;
    }



    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    @Column(name = "TO_STATUS")
    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }


}
