package com.jumbo.wms.model.warehouse;

public enum HandOverListStatus {
    CANCELED(0), // 取消
    CREATED(1), // 新建
    FINISHED(10); // 完成

    private int value;

    private HandOverListStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static HandOverListStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CANCELED;
            case 1:
                return CREATED;
            case 10:
                return FINISHED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
