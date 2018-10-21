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
@Table(name = "T_WH_SHIPPING")
public class WmsShippingQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7660978360041185672L;
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
    private WmsConfirmOrderQueue queue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SHIPPING", sequenceName = "S_T_WH_SHIPPING", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_WH_SHIPPING", strategy = GenerationType.SEQUENCE)
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

    @ManyToOne
    @JoinColumn(name = "CONFIRM_ORDER_ID")
    public WmsConfirmOrderQueue getQueue() {
        return queue;
    }

    public void setQueue(WmsConfirmOrderQueue queue) {
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
