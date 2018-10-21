package com.jumbo.wms.model.warehouse;

/**
 * @author dly
 * 
 */
public enum ReturnPackageStatus {
    REFUSE_TO_ACCEPT(0), // 已拒收
    ALREADY_RECEIVED(1), // 已收件
    IN_PROCESSING(5), // 处理中
    ALREADY_IN_STORAGE(10); // 已入库

    private int value;

    private ReturnPackageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReturnPackageStatus valueOf(int value) {
        switch (value) {
            case 0:
                return REFUSE_TO_ACCEPT;
            case 1:
                return ALREADY_RECEIVED;
            case 5:
                return IN_PROCESSING;
            case 10:
                return ALREADY_IN_STORAGE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
