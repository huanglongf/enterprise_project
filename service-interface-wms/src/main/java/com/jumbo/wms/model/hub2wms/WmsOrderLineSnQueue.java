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
 * HUB2WMS过仓 订单发票信息
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SO_LINE_SN")
public class WmsOrderLineSnQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2702767999050409063L;
    /*
     * PK
     */
    private Long id;
    /*
     * 发票抬头
     */
    private String sn;
    /*
     * 发票编码
     */
    private WmsSalesOrderLineQueue wmsSalesOrderLineQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SO_LINE_SN", sequenceName = "S_T_WH_SO_LINE_SN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_SO_LINE_SN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN", length = 50)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINE_ID")
    @Index(name = "IDX_SN_SO_LINE_ID")
    public WmsSalesOrderLineQueue getWmsSalesOrderLineQueue() {
        return wmsSalesOrderLineQueue;
    }

    public void setWmsSalesOrderLineQueue(WmsSalesOrderLineQueue wmsSalesOrderLineQueue) {
        this.wmsSalesOrderLineQueue = wmsSalesOrderLineQueue;
    }



}
