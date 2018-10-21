package com.jumbo.wms.model.hub2wms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_SHIPPING_LINE")
public class WmsShippingLineQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4009815734509396091L;
    /*
     * PK
     */
    private Long id;
    /*
     * 平台行号 
     */
    private String lineNo;
    /*
     * 商品
     */
    private String sku;
    /*
     * 数量
     */
    private long qty;
    /*
     * SHIPPING头
     */
    private WmsShippingQueue shippingQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SHIPPING_LINE", sequenceName = "S_T_WH_SHIPPING_LINE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_WH_SHIPPING_LINE", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "SKU", length = 50)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "QTY")
    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    @ManyToOne
    @JoinColumn(name = "SHIPPING_ID")
    public WmsShippingQueue getShippingQueue() {
        return shippingQueue;
    }

    public void setShippingQueue(WmsShippingQueue shippingQueue) {
        this.shippingQueue = shippingQueue;
    }



}
