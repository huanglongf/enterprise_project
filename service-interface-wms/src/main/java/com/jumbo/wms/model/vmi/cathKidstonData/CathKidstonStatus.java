package com.jumbo.wms.model.vmi.cathKidstonData;


public enum CathKidstonStatus {
    NEW(1), // 未处理
    FINISHED(10), // 已完成
    FAILED(0), // 处理失败
    ;
    private int value;

    private CathKidstonStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CathKidstonStatus valueOf(int value) {
        switch (value) {
            case 1:
                return NEW;
            case 10:
                return FINISHED;
            case 0:
                return FAILED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
