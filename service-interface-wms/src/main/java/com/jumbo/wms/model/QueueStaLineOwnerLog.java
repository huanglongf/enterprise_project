package com.jumbo.wms.model;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.warehouse.QueueLogStaLine;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_q_sta_line_owner_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class QueueStaLineOwnerLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 6359529854321673240L;
    /**
     * PK
     */
    private Long id;
    /**
     * 商品code
     */
    private String skuCode;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 明细行
     */
    private QueueLogStaLine QueueStaLine;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_Q_STA_LINE_OWNER", sequenceName = "S_T_WH_Q_STA_LINE_OWNER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_Q_STA_LINE_OWNER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "skuCode")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QLG_STA_LINE_ID")
    public QueueLogStaLine getQueueStaLine() {
        return QueueStaLine;
    }

    public void setQueueStaLine(QueueLogStaLine queueStaLine) {
        QueueStaLine = queueStaLine;
    }

}
