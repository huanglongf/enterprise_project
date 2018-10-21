package com.jumbo.wms.model.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 出入库流水归档
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_Inv_Log_OMS_ARCHIVE")
public class wmsInfoLogOmsArchive extends BaseModel {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -4092793344876572711L;
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
    private Long wmsOrderStatusOms;

    /**
     * 是否可售
     */
    private Boolean isSales;

    @Id
    @Column(name = "ID")
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

    @Column(name = "orderstatus")
    public Long getWmsOrderStatusOms() {
        return wmsOrderStatusOms;
    }

    public void setWmsOrderStatusOms(Long wmsOrderStatusOms) {
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
