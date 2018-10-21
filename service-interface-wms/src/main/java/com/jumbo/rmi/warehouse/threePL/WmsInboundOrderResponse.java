package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓入库反馈
 * 
 */
public class WmsInboundOrderResponse implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -544841721090486032L;
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
     * 实际入库时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String inboundTime;

    /**
     * 单据类型:1,收货入库
     */
    private String type;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 入库明细
     */
    private WmsInboundOrderResponseLine[] lines;

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

    public String getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(String inboundTime) {
        this.inboundTime = inboundTime;
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

    public WmsInboundOrderResponseLine[] getLines() {
        return lines;
    }

    public void setLines(WmsInboundOrderResponseLine[] lines) {
        this.lines = lines;
    }

}
