package com.jumbo.wms.model.warehouse;


/**
 * 货物运送方式
 * 
 * @author lihui
 *
 * @createDate 2015年8月18日 上午11:40:04
 */
public enum FreightMode {
    SELF_PICKUP(10), // 上门自取
    LOGISTICS_DELIVERY(20);// 物流配送
    private int value;

    private FreightMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FreightMode valueOf(int value) {
        switch (value) {
            case 10:
                return SELF_PICKUP;
            case 20:
                return LOGISTICS_DELIVERY;
            default:
                return null;
        }
    }
}
