package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class WmsOrderCancelNotify implements Serializable {

    private static final long serialVersionUID = -3322056802799718461L;
    /**
     * 货主ID
     */
    private String ownerUserId;
    /**
     * 仓储中心订单编码
     */
    private String orderCode;
    /**
     * 订单类型: 201 交易出库单 501 销退入库单 502 换货出库单 301 调拨出库单 302 调拨入库单 601 采购入库单 901 普通出库单 904 普通入库单 604
     * B2B干线退货入库 306 B2B入库单 305 B2B出库单 703 库存状态调整出库 704 库存状态调整入库 1102 加工服务单 707 转移单 1101 增值服务单
     */
    private String orderType;

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
