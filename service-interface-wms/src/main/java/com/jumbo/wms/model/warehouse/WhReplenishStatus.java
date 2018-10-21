package com.jumbo.wms.model.warehouse;
/**
 * 库内补货报表状态
 * @author jinlong.ke
 *
 */
public enum WhReplenishStatus {
    CREATED(1), //
    FINISHED(10), //
    DOING(3), //
    CANCEL(0);//
    private int value;

    private WhReplenishStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WhReplenishStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 10:
                return FINISHED;
            case 3:
                return DOING;
            case 0:
                return CANCEL;
            default:
                throw new IllegalArgumentException();
        }
    }
}
