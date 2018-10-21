package com.jumbo.wms.model.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_SHIPPING_ARCHIVE")
public class ShippingArchive extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8657613960186730404L;
	/*
     * PK
     */
    private Long id;
    /*
     * 单据号，Wms单据唯一标识
     */
    private String shippingCode;
    /*
     * 仓库编码
     */
    private String whCode;
    /*
     * 物流商简称
     */
    private String transCode;
    /*
     * 运单号
     */
    private String transNo;
    /*
     * 确认单据头
     */
    private Long queue;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPPING_CODE", length = 30)
    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    @Column(name = "TRANS_CODE", length = 20)
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @Column(name = "TRANS_NO", length = 50)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "CONFIRM_ORDER_ID")
    public Long getQueue() {
        return queue;
    }

    public void setQueue(Long queue) {
        this.queue = queue;
    }

    @Column(name = "WH_CODE", length = 20)
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }


}
