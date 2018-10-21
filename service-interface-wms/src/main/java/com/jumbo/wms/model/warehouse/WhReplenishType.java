package com.jumbo.wms.model.warehouse;

/**
 * 库内补货报表类型
 * 
 * @author jinlong.ke
 * 
 */
public enum WhReplenishType {
    NORMAL(1),
    PICKING_FAILED(2);
    private int value;

    private WhReplenishType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WhReplenishType valueOf(int value) {
        switch (value) {
            case 1:
                return NORMAL;
            case 2:
                return PICKING_FAILED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
