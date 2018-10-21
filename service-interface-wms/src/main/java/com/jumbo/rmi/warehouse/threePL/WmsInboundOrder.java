package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓入库单据
 * 
 */
public class WmsInboundOrder implements Serializable {

    private static final long serialVersionUID = -667916473900990669L;
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
     * 预计到货时间 格式：年(4位)月(2位)日（2位）时（2位）分（2位）秒（2位）
     */
    private String arriveTime;

    /**
     * 单据类型:1,收货入库,41 退货入库
     */
    private String type;
    /**
     * 货物所属
     */
    private String owner;
    /**
     * 单据备注
     */
    private String memo;
    /**
     * 入库单明细行
     */
    private WmsInboundOrderLine[] lines;
    /**
     * 联系人
     */
    private String contactor;
    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 联系人电话
     */
    private String mobile;

    /**
     * 运单号
     */
    private String transNo;
    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

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

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public WmsInboundOrderLine[] getLines() {
        return lines;
    }

    public void setLines(WmsInboundOrderLine[] lines) {
        this.lines = lines;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
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
}
