package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓库数量调整明细
 * 
 */
public class WmsInvChangeOrderResponse implements Serializable {

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
     * 外包仓相关单据
     */
    private String slipCode1;
    /**
     * 外包仓相关单据
     */
    private String slipCode2;

    /**
     * 实际调整时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String orderDate;

    /**
     * 备注
     */
    private String memo;
    /**
     * 调整类型 51：增量 52：全量覆盖
     */
    private String type;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 调整明细
     */
    private WmsInvChangeOrderLineResponse[] lines;

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

    public WmsInvChangeOrderLineResponse[] getLines() {
        return lines;
    }

    public void setLines(WmsInvChangeOrderLineResponse[] lines) {
        this.lines = lines;
    }

}
