package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓出库反馈
 * 
 */
public class WmsOutboundOrderResponse implements Serializable {

    private static final long serialVersionUID = 286246537486079918L;

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
     * 实际出库时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String outboundTime;

    /**
     * 单据类型:10,转出,21销售出
     */
    private String type;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 出库明细
     */
    private WmsOutboundOrderResponseLine[] lines;

    /**
     * SN列表
     */
    private WmsOutboundOrderResponseSnLine[] snLines;

    /**
     * 物流商
     */
    private String lpCode;

    /**
     * 快递单号
     */
    private String transNos;

    /**
     * 耗材商品编码, 如有多个使用“,”分隔
     */
    private String materialSkus;

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

    public String getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(String outboundTime) {
        this.outboundTime = outboundTime;
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

    public WmsOutboundOrderResponseLine[] getLines() {
        return lines;
    }

    public void setLines(WmsOutboundOrderResponseLine[] lines) {
        this.lines = lines;
    }

    public WmsOutboundOrderResponseSnLine[] getSnLines() {
        return snLines;
    }

    public void setSnLines(WmsOutboundOrderResponseSnLine[] snLines) {
        this.snLines = snLines;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getTransNos() {
        return transNos;
    }

    public void setTransNos(String transNos) {
        this.transNos = transNos;
    }

    public String getMaterialSkus() {
        return materialSkus;
    }

    public void setMaterialSkus(String materialSkus) {
        this.materialSkus = materialSkus;
    }

}
