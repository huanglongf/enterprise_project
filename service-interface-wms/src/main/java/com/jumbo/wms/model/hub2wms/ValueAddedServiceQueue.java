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
 * HUB2WMS过仓
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_VALUE_ADDSERVICE")
public class ValueAddedServiceQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9122939201992868903L;
    /*
     * PK
     */
    private Long id;

    /*
     * 类型 
     */
    private int type;
    /*
     * 卡号
     */
    private String cardCode;
    /*
     * 备注
     */
    private String memo;
    /*
     * 销售或者换货出库单据头
     */
    private WmsSalesOrderQueue wmsSalesOrderQueue;

    /*
     * 销售或者换货出库单据明细行
     */
    private WmsSalesOrderLineQueue wmsSalesOrderLineQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_VALUE_ADDSERVICE", sequenceName = "S_T_WH_VALUE_ADDSERVICE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_VALUE_ADDSERVICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", precision = 6)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "CARD_CODE", length = 50)
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Column(name = "MEMO", length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_RO_ID")
    @Index(name = "IDX_VALUESERVICE_SOROID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_LINE_ID")
    @Index(name = "IDX_VALUESERVICE_LINEID")
    public WmsSalesOrderLineQueue getWmsSalesOrderLineQueue() {
        return wmsSalesOrderLineQueue;
    }

    public void setWmsSalesOrderLineQueue(WmsSalesOrderLineQueue wmsSalesOrderLineQueue) {
        this.wmsSalesOrderLineQueue = wmsSalesOrderLineQueue;
    }


}
