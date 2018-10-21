package com.jumbo.wms.model.vmi.warehouse;

public enum WlbLogType {
    WLBSTOCKIN(0), // 创建入库单
    WLBSTOCKOUT(1), // 创建出库单
    WLBSALESORDER(2), // 创建销售单
    WLBREFUNDORDER(3), // 创建退单
    WLBSKUMAPPING(4), // 物流宝商品映射
    WLBSTOCKINFEEDBACK(10), // 创建入库单反馈
    WLBSTOCKOUTFEEDBACK(11), // 创建出库反馈
    WLBSALESORDERFEEDBACK(12), // 创建销售单反馈
    WLBREFUNDORDERFEEDBACK(13); // 退单反馈

    private int value;

    private WlbLogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;



    }

    public static WlbLogType valueOf(int value) {
        switch (value) {
            case 0:
                return WLBSTOCKIN;
            case 1:
                return WLBSTOCKOUT;
            case 2:
                return WLBSALESORDER;
            case 3:
                return WLBREFUNDORDER;
            case 4:
                return WLBSKUMAPPING;
            case 10:
                return WLBSTOCKINFEEDBACK;
            case 11:
                return WLBSTOCKOUTFEEDBACK;
            case 12:
                return WLBSALESORDERFEEDBACK;
            case 13:
                return WLBREFUNDORDERFEEDBACK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
