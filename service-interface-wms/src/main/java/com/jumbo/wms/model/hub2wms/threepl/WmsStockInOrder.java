package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WmsStockInOrder implements Serializable {

    private static final long serialVersionUID = 8157769820685896667L;
    /**
     * 仓储中心入库订单编码
     */
    private String orderCode;
    /**
     * 单据类型： 302调拨入库单 501销退入库单 601采购入库单 904普通入库单306 B2B入库单 604 B2B干线退货入库 704 库存状态调整入库
     */
    private Integer orderType;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 支持入库单多次确认(如：每上架一次就立刻回传) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0
     */
    private Integer confirmType;
    /**
     * 仓库入库单完成时间
     */
    private Date orderConfirmTime;
    /**
     * 入库单商品信息列表
     */
    private List<StockInOrderItem> orderItems;
    /**
     * 入库单商品校验信息列表
     */
    private List<StockInCheckItem> checkItems;

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

    public List<StockInOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<StockInOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<StockInCheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<StockInCheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    @Override
    public String toString() {
        return "StockInOrder [orderCode=" + orderCode + ", orderType=" + orderType + ", outBizCode=" + outBizCode + ", confirmType=" + confirmType + ", orderConfirmTime=" + orderConfirmTime + ", orderItems=" + orderItems + ", checkItems=" + checkItems
                + "]";
    }
}
