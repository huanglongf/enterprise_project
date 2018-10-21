package com.jumbo.wms.model.hub2wms.threepl;

/**
 * 菜鸟入库通知单单据类型
 */
public enum OrderType {
    TRANSFER(302), // 调拨入库单
    SALES_RETURN(501), // 销退入库单
    PURCHASE(601), // 采购入库单
    COMMON(904), // 普通入库单
    B2B(306), // B2B入库单
    B2B_TRUNK_LINE_RETURN(604), // B2B干线退货入库
    INVENTORY_STATUS_ADJUST(704)// 库存状态调整入库
    ;
    private int value;

    private OrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderType valueOf(int value) {
        switch (value) {
            case 302:
                return TRANSFER;
            case 501:
                return SALES_RETURN;
            case 601:
                return PURCHASE;
            case 904:
                return COMMON;
            case 306:
                return B2B;
            case 604:
                return B2B_TRUNK_LINE_RETURN;
            case 704:
                return INVENTORY_STATUS_ADJUST;
            default:
                return COMMON;
        }
    }
}
