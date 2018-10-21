package com.jumbo.wms.model.vmi.Default;

public enum VmiGeneralStatus {
    LOCKED(0), // 锁定
    NEW(1), // 新建/未发送
    PARTFINISH(5), // 部分完成
    FINISHED(10), // 已完成/发送成功
    REPEATORDER(11), // 重复订单
    CANCEL(17), // 取消
    FAILED(20), // 处理失败/待邮件通知
    EMAILFINISH(21),// 邮件通知成功，待处理
    NO_FEEDBACK_INTERFACE(33);// 未定义反馈接口,数据无需反馈
    private int value;

    private VmiGeneralStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VmiGeneralStatus valueOf(int value) {
        switch (value) {
            case 0:
                return LOCKED;
            case 1:
                return NEW;
            case 5:
                return PARTFINISH;
            case 10:
                return FINISHED;
            case 11:
                return REPEATORDER;
            case 17:
                return CANCEL;
            case 20:
                return FAILED;
            case 21:
                return EMAILFINISH;
            case 33:
                return NO_FEEDBACK_INTERFACE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
