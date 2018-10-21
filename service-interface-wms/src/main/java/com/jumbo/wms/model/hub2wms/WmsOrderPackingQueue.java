package com.jumbo.wms.model.hub2wms;

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
 * HUB2WMS过仓 单据包装
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_ORDER_PACKING")
public class WmsOrderPackingQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5175154317913705117L;
    /*
     * PK
     */
    private Long id;
    /*
     * 类型 
     */
    private int type;
    /*
     * 商品
     */
    private String sku;
    /*
     * 说明
     */
    private String memo;

    /*
     * 销售订单头
     */
    private WmsSalesOrderQueue wmsSalesOrderQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ORDER_PACKING", sequenceName = "S_T_WH_ORDER_PACKING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ORDER_PACKING")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", precision = 4)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "MEMO", length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_ID")
    @Index(name = "IDX_PACKING_HW_SO_ID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }



}
