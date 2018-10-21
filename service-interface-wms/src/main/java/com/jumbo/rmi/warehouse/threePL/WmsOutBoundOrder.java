package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓出库单据
 * 
 */
public class WmsOutBoundOrder implements Serializable {

    private static final long serialVersionUID = -8133188889558216327L;

    /**
     * 数据唯一标识
     */
    private String uuid;
    /**
     * 仓库编码（WMS分配给第三方仓储编码）
     */
    private String warehouseCode;
    /**
     * 入库单唯一对接编码
     */
    private String orderCode;
    /**
     * 相关单据（提供宝尊系统前端业务单据号）
     */
    private String slipCode;
    /**
     * 相关单据1(相关原始单据编码)
     */
    private String slipCode1;
    /**
     * 相关单据2(平台单据编码)
     */
    private String slipCode2;
    /**
     * 宝尊WMS单据创建日期 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String orderDate;
    /**
     * 单据类型 10：转出
     */
    private String type;
    /**
     * 货物所属
     */
    private String owner;

    /**
     * OTO门店编码
     */
    private String toLocation;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 出库单明细
     */
    private WmsOutBoundOrderLine[] lines;

    /**
     * 订单发货信息
     */
    private WmsOutBoundOrderDelivery deliveryInfo;

   

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public WmsOutBoundOrderLine[] getLines() {
        return lines;
    }

    public void setLines(WmsOutBoundOrderLine[] lines) {
        this.lines = lines;
    }

    public WmsOutBoundOrderDelivery getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(WmsOutBoundOrderDelivery deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

}
