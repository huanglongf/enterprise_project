package com.jumbo.wms.model.hub2wms;

import java.util.Date;

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
 * 出入库流水
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_Inv_Log_OMS")
public class WmsInfoLogOms extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -8018466309238597698L;
    /**
     * ID
     */
    private Long id;
    /**
     * 商品
     */
    private String sku;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 流水操作时间
     */
    private Date tranTime;
    /**
     * 店铺
     */
    private String owner;
    /**
     * Sn号
     */
    private String sns;
    /**
     * 批次号
     */
    private String btachCode;
    /**
     * 商品批次
     */
    private String barchNo;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 仓库编码
     */
    private String warehouceCode;
    /**
     * 订单出入库状态
     */
    private WmsOrderStatusOms wmsOrderStatusOms;

    /**
     * 是否可售
     */
    private Boolean isSales;
    
    
    
    private String lineNo;
    
    
    
    @Column(name = "line_no")
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_INV_LOG_OMS", sequenceName = "S_t_wh_Inv_Log_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_INV_LOG_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "sku")
    public String getSku() {
        return sku;
    }

    @Column(name = "tran_time")
    public Date getTranTime() {
        return tranTime;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "sns")
    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    @Column(name = "btachCode")
    public String getBtachCode() {
        return btachCode;
    }

    public void setBtachCode(String btachCode) {
        this.btachCode = btachCode;
    }

    @Column(name = "barchNo")
    public String getBarchNo() {
        return barchNo;
    }

    public void setBarchNo(String barchNo) {
        this.barchNo = barchNo;
    }

    @Column(name = "invStatus")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "warehouceCode")
    public String getWarehouceCode() {
        return warehouceCode;
    }

    public void setWarehouceCode(String warehouceCode) {
        this.warehouceCode = warehouceCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderstatus")
    @Index(name = "IDX_ORDER_STATUS")
    public WmsOrderStatusOms getWmsOrderStatusOms() {
        return wmsOrderStatusOms;
    }

    public void setWmsOrderStatusOms(WmsOrderStatusOms wmsOrderStatusOms) {
        this.wmsOrderStatusOms = wmsOrderStatusOms;
    }

    @Column(name = "is_sales")
    public Boolean getIsSales() {
        return isSales;
    }

    public void setIsSales(Boolean isSales) {
        this.isSales = isSales;
    }

}
