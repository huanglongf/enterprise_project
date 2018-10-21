package com.jumbo.wms.model;

public enum CommonSyncStatus {
    CREATED(0), // 新建
    TO_SYNC(2), // 待同步
    SYNC_FAILED(3), // 同步失败
    SYNC_PASS(5), // 跳过不处理
    SYNC_SUCCESS(10);// 同步成功

    private int value;

    private CommonSyncStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static CommonSyncStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CREATED;
            case 2:
                return TO_SYNC;
            case 3:
                return SYNC_FAILED;
            case 5:
                return SYNC_PASS;
            case 10:
                return SYNC_SUCCESS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
