package com.jumbo.wms.model.warehouse;

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

import com.jumbo.wms.model.BaseModel;

/**
 * 缺货信息日志
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_q_sta_line_stockout_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueStaLineStockoutLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -366084085211509785L;
    /**
     * PK
     */
    private Long id;
    /**
     * 商品code
     */
    private String skuCode;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 明细行
     */
    private QueueLogStaLine logStaLine;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_t_wh_q_sta_line_stockout_log", sequenceName = "S_t_wh_q_sta_line_stockout_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_t_wh_q_sta_line_stockout_log")
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

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QLG_STA_LINE_ID")
    public QueueLogStaLine getLogStaLine() {
        return logStaLine;
    }

    public void setLogStaLine(QueueLogStaLine logStaLine) {
        this.logStaLine = logStaLine;
    }


}
