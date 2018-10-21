package com.jumbo.wms.model.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
@Entity
@Table(name = "T_WH_CONFIRM_ORDER_ARCHIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ConfirmOrderArchive extends BaseModel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5889169756961511567L;
	/*
     * PK
     */
    private Long id;
    /*
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /*
     * 订单所属
     */
    private String owner;
    /*
     * 订单类型
     */
    private int orderType;
    /*
     * 是否拆单
     */
    private boolean IsDs;
    /*
     * 状态 
     */
    private boolean status;
    /*
     * 备注 
     */
    private String memo;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 是否推送PAC
     */
    private boolean ispush;
    /*
     * NIKE特殊逻辑
     */
    private Boolean ismeet;
    /*
     * 系统来源标示
     */
    private String systemKey;


    private String refWarehouseCode; // 计划仓库code

    private String errorStatus;// 错误状态


    private String orderSourcePlatform;// 订单来源

    @Column(name = "ORDER_SOURCE_PLATFORM")
    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "ERROR_STATUS")
    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Column(name = "PLAN_WAREHOUSE_CODE")
    public String getRefWarehouseCode() {
        return refWarehouseCode;
    }

    public void setRefWarehouseCode(String refWarehouseCode) {
        this.refWarehouseCode = refWarehouseCode;
    }

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE", length = 30)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "OWNER", length = 50)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "ORDER_TYPE")
    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Column(name = "IS_DS")
    public boolean isIsDs() {
        return IsDs;
    }

    public void setIsDs(boolean isDs) {
        IsDs = isDs;
    }

    @Column(name = "STATUS")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SYSTEM_KEY", length = 30)
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "is_push")
    public boolean isIspush() {
        return ispush;
    }

    public void setIspush(boolean ispush) {
        this.ispush = ispush;
    }

    @Column(name = "is_meet")
    public Boolean getIsmeet() {
        return ismeet;
    }

    public void setIsmeet(Boolean ismeet) {
        this.ismeet = ismeet;
    }


}
