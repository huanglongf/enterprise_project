package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.warehouse.PackageInfo;

public class WmsStockOutOrderConfirm implements Serializable {

    private static final long serialVersionUID = -6685797606233403269L;

    /**
     * 仓储中心出库订单编码
     */
    private String orderCode;
    /**
     * 操作类型：301 调拨出库单 901 普通出库单 (如货主拉走一部分货) 305 B2B出库单 704 库存状态调整出库
     */
    private Integer orderType;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 支持出库单多次确认(如:) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0
     */
    private Integer confirmType;
    /**
     * 仓库出库单完成时间
     */
    private Date orderConfirmTime;
    /**
     * 出库单商品信息列表
     */
    private List<StockOutOrderItem> orderItems;
    /**
     * 包裹列表
     */
    private List<PackageInfo> packageInfos;
    /**
     * 出库单商品校验信息列表
     */
    private List<StockOutCheckItem> checkItems;

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

    public List<StockOutOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<StockOutOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<PackageInfo> getPackageInfos() {
        return packageInfos;
    }

    public void setPackageInfos(List<PackageInfo> packageInfos) {
        this.packageInfos = packageInfos;
    }

    public List<StockOutCheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<StockOutCheckItem> checkItems) {
        this.checkItems = checkItems;
    }
}
