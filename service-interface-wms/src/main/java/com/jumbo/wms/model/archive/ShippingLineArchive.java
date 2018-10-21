package com.jumbo.wms.model.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
@Entity
@Table(name = "T_WH_SHIPPING_LINE_ARCHIVE")
public class ShippingLineArchive extends BaseModel {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5338201705326986385L;
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
    private Long shippingQueue;

    @Id
    @Column(name = "ID")
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

    @Column(name = "SHIPPING_ID")
    public Long getShippingQueue() {
        return shippingQueue;
    }

    public void setShippingQueue(Long shippingQueue) {
        this.shippingQueue = shippingQueue;
    }


}
