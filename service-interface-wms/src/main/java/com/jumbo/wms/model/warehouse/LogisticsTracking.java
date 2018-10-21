package com.jumbo.wms.model.warehouse;

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
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_STA_LOGISTICS_TRACKING")
public class LogisticsTracking extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -6869266022945215815L;
    /**
     * PK ID
     */
    private Long id;
    /**
     * 货主
     */
    private String company;
    /**
     * 仓库
     */
    private String warehouse;
    /**
     * ERP单号 作业单号
     */
    private String erpOrder;
    /**
     * 顺丰出库单号
     */
    private String shipMentId;
    /**
     * 运单号
     */
    private String wayBillNo;
    /**
     * 发货时间
     */
    private Date actualShipDateTime;
    /**
     * 承运商名字
     */
    private String carrier;
    /**
     * 承运商服务类型
     */
    private String carrierService;
    /**
     * 顺丰操作人
     */
    private String userStamp;
    /**
     * 状态产生时间
     */
    private Date statusTime;
    /**
     * 状态编码
     */
    private String statusCode;
    /**
     * 状态说明
     */
    private String statusRemark;
    /**
     * WMS对应物流商
     */
    private String lpCode;
    /**
     * 作业单
     */
    private StockTransApplication sta;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_LOGISTICS_TRACKING", sequenceName = "S_T_WH_STA_LOGISTICS_TRACKING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_LOGISTICS_TRACKING")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "COMPANY")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "WAREHOUSE")
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "ERP_ORDER")
    public String getErpOrder() {
        return erpOrder;
    }

    public void setErpOrder(String erpOrder) {
        this.erpOrder = erpOrder;
    }

    @Column(name = "SHIP_MENT_ID")
    public String getShipMentId() {
        return shipMentId;
    }

    public void setShipMentId(String shipMentId) {
        this.shipMentId = shipMentId;
    }

    @Column(name = "WAY_BILL_NO")
    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    @Column(name = "ACTUAL_SHIP_DATE_TIME")
    public Date getActualShipDateTime() {
        return actualShipDateTime;
    }

    public void setActualShipDateTime(Date actualShipDateTime) {
        this.actualShipDateTime = actualShipDateTime;
    }

    @Column(name = "CARRIER")
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Column(name = "CARRIER_SERVICE")
    public String getCarrierService() {
        return carrierService;
    }

    public void setCarrierService(String carrierService) {
        this.carrierService = carrierService;
    }

    @Column(name = "USER_STAMP")
    public String getUserStamp() {
        return userStamp;
    }

    public void setUserStamp(String userStamp) {
        this.userStamp = userStamp;
    }

    @Column(name = "STATUS_TIME")
    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    @Column(name = "STATUS_CODE")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Column(name = "STATUS_REMARK")
    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

    @Column(name = "LP_CODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_LOGISTICS_TRACKING")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }
}
