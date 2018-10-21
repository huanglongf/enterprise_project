package com.jumbo.wms.model.baseinfo;

public enum SkuSnCardStatus {

    NONACTIVATED(0), // 未激活
    ACTIVATE_SUCCESS(1), // 激活成功
    ACTIVATE_REPETITIVE_OPERATION(2), // 激活重复操作
    ACTIVATE_ERROR(3), // 激活失败
    FROZEN(21), // 已冻结
    WAIT_UNFREEZE(22), // 待解冻
    RELEASE_SUCCESS(25), // 解冻成功
    RELEASE_ERROR(27), // 解冻失败
    SELECT_CARD(50), // 卡查询
    CANCEL(99), // 已作废
    CANCEL_FAIL(100);// 作废失败

    private int value;

    private SkuSnCardStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkuSnCardStatus valueOf(int value) {
        switch (value) {
            case 0:
                return NONACTIVATED;
            case 1:
                return ACTIVATE_SUCCESS;
            case 2:
                return ACTIVATE_REPETITIVE_OPERATION;
            case 3:
                return ACTIVATE_ERROR;
            case 21:
                return FROZEN;
            case 22:
                return WAIT_UNFREEZE;
            case 25:
                return RELEASE_SUCCESS;
            case 27:
                return RELEASE_ERROR;
            case 50:
                return SELECT_CARD;
            case 99:
                return CANCEL;
            case 100:
                return CANCEL_FAIL;
            default:
                return NONACTIVATED;
        }
    }
}
