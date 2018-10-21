package com.jumbo.wms.model.warehouse;

public enum PackageInfoStatus {

    CREATED(1), // 新建
    OUTBOUND(5), // 出库
    FINISHED(10);// 完成

    private int value;

    private PackageInfoStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PackageInfoStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 5:
                return OUTBOUND;
            case 10:
                return FINISHED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
