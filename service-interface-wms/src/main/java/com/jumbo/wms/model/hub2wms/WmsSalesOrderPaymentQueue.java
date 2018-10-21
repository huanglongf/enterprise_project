package com.jumbo.wms.model.hub2wms;

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

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS 过仓订单支付信息
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_ORDER_PAYMENT")
public class WmsSalesOrderPaymentQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4161526610607318940L;
    /*
     * PK
     */
    private Long id;
    /*
     * 金额
     */
    private BigDecimal amt;
    /*
     * 类型 
     */
    private int type;
    /*
     * 支付说明
     */
    private String memo;
    /*
     * 销售订单头
     */
    private WmsSalesOrderQueue wmsSalesOrderQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ORDER_PAYMENT", sequenceName = "S_T_WH_ORDER_PAYMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ORDER_PAYMENT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AMT", precision = 15, scale = 2)
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "TYPE", precision = 4, scale = 0)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "MEMO", length = 100)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_ID")
    @Index(name = "IDX_PAYMENT_SO_ID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }



}
