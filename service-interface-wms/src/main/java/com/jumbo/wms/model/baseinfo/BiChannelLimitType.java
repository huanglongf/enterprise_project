package com.jumbo.wms.model.baseinfo;

/**
 * 渠道类型
 * 
 * @author dly
 * 
 */
public enum BiChannelLimitType {
    INTRANSIT(1), // 正常
    OCCUPIED(2); // 配货中
    private int value;

    private BiChannelLimitType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static BiChannelLimitType valueOf(int value) {
        switch (value) {
            case 1:
                return INTRANSIT;
            case 2:
                return OCCUPIED;
            default:
                return INTRANSIT;
        }
    }
}
