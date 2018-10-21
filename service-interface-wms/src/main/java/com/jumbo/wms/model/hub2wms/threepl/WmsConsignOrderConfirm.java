package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WmsConsignOrderConfirm implements Serializable {

    private static final long serialVersionUID = 490462309458370697L;
    /**
     * 仓储中心订单编码
     */
    private String orderCode;
    /**
     * 订单类型（201 一般交易出库单、502 换货出库单）
     */
    private Integer orderType;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 支持发货单多次确认(如:分包裹出库):0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0
     */
    private Integer confirmType;
    /**
     * 仓库订单完成时间
     */
    private Date orderConfirmTime;
    /**
     * 订单商品信息列表
     */
    private List<ConsignOrderItem> orderItems;
    /**
     * 运单信息列表
     */
    private List<ConsignTmsOrder> tmsOrders;
    /**
     * 发票确认信息列表
     */
    private List<ConsignInvoinceConfirm> invoinceConfirms;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public Integer getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public Date getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(Date orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

    public List<ConsignOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ConsignOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<ConsignTmsOrder> getTmsOrders() {
        return tmsOrders;
    }

    public void setTmsOrders(List<ConsignTmsOrder> tmsOrders) {
        this.tmsOrders = tmsOrders;
    }

    public List<ConsignInvoinceConfirm> getInvoinceConfirms() {
        return invoinceConfirms;
    }

    public void setInvoinceConfirms(List<ConsignInvoinceConfirm> invoinceConfirms) {
        this.invoinceConfirms = invoinceConfirms;
    }

}
