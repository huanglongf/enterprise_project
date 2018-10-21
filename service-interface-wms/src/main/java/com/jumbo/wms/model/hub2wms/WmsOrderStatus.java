package com.jumbo.wms.model.hub2wms;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 订单取消结果
 * 
 * @author cheng.su
 * 
 */
public class WmsOrderStatus extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4811256459740277601L;
    /**
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /**
     * 单据号，Wms单据唯一标识
     */
    private String shippingCode;
    /**
     * 订单类型
     */
    private int type;
    /**
     * 状态
     */
    private int status;
    /**
     * 物流信息
     */
    private List<WmsTransInfo> transInfos;
    /**
     * 发票信息
     */
    private List<WmsOrderInvoice> invoices;
    /**
     * 库存流水
     */
    private List<WmsInvLog> invLogs;


    private String sourceOrderCode;// 业务单据编号

    private Date outboundTime;// 出库时间

    private String refWarehouseCode; // 仓库编码

    private Date finishTime;// 退货收件时间

    private List<WmsOrderLine> wmsOrderLines; // 明细


    private Date createTime;// 时间

    private Date otherOutboundTime;// 报表出库时间


    /**
     * 店铺
     */
    private String owner;

    /**
     * 入库时间
     */
    private Date inboundTime;
    
    private String systemKey;
    
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public Date getOtherOutboundTime() {
        return otherOutboundTime;
    }

    public void setOtherOutboundTime(Date otherOutboundTime) {
        this.otherOutboundTime = otherOutboundTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRefWarehouseCode() {
        return refWarehouseCode;
    }

    public void setRefWarehouseCode(String refWarehouseCode) {
        this.refWarehouseCode = refWarehouseCode;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public List<WmsOrderLine> getWmsOrderLines() {
        return wmsOrderLines;
    }

    public void setWmsOrderLines(List<WmsOrderLine> wmsOrderLines) {
        this.wmsOrderLines = wmsOrderLines;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<WmsTransInfo> getTransInfos() {
        return transInfos;
    }

    public void setTransInfos(List<WmsTransInfo> transInfos) {
        this.transInfos = transInfos;
    }

    public List<WmsOrderInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<WmsOrderInvoice> invoices) {
        this.invoices = invoices;
    }

    public List<WmsInvLog> getInvLogs() {
        return invLogs;
    }

    public void setInvLogs(List<WmsInvLog> invLogs) {
        this.invLogs = invLogs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }


}
