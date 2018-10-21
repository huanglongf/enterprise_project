package com.jumbo.wms.model.hub2wms;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class WmsConfirmOrder extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 609166552336464188L;
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
     * 包裹列表
     */
    private List<WmsShipping> Shippings;
    
    private String statusType;


    private String refWarehouseCode; // 计划仓库code

    private String orderSourcePlatform;// 订单来源

    private List<WmsOrderLine> lines;// 确认单明细

    private WmsOrderStatus orderStatus;//

    private String errorStatus;// 错误状态

    private String sourceOrderCode; // TAOBAO的原订单编号

    private Date otherOutboundTime;// 报表出库时间

    private String systemKey;

    public Date getOtherOutboundTime() {
        return otherOutboundTime;
    }

    public void setOtherOutboundTime(Date otherOutboundTime) {
        this.otherOutboundTime = otherOutboundTime;
    }

    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public WmsOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(WmsOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<WmsOrderLine> getLines() {
        return lines;
    }

    public void setLines(List<WmsOrderLine> lines) {
        this.lines = lines;
    }

    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    public String getRefWarehouseCode() {
        return refWarehouseCode;
    }

    public void setRefWarehouseCode(String refWarehouseCode) {
        this.refWarehouseCode = refWarehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public boolean isIsDs() {
        return IsDs;
    }

    public void setIsDs(boolean isDs) {
        IsDs = isDs;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<WmsShipping> getShippings() {
        return Shippings;
    }

    public void setShippings(List<WmsShipping> shippings) {
        Shippings = shippings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }
    
}
