package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓库存状态反馈调整
 * 
 */
public class WmsInvStatusChangeOrderResponse implements Serializable {

    private static final long serialVersionUID = 5792441954710967516L;
    /**
     * 数据唯一标识
     */
    private String uuid;
    /**
     * 来源(标识第三方系统编码)
     */
    private String customer;
    /**
     * 仓库编码（WMS分配给第三方仓储编码）
     */
    private String warehouseCode;
    /**
     * 入库单唯一对接编码
     */
    private String orderCode;

    /**
     * 实际调整时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String orderDate;

    /**
     * 备注
     */
    private String memo;
    /**
     * 单据类型 50：转出
     */
    private String type;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    private String owner;

    /**
     * 调整明细
     */
    private WmsInvStatusChangeOrderLineResponse[] lines;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public WmsInvStatusChangeOrderLineResponse[] getLines() {
        return lines;
    }

    public void setLines(WmsInvStatusChangeOrderLineResponse[] lines) {
        this.lines = lines;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}
