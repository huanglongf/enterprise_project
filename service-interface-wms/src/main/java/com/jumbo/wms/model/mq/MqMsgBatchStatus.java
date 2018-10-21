package com.jumbo.wms.model.mq;

public enum MqMsgBatchStatus {
    CREATED(1), // 新建
    SENDED(5), // 已发送
    CONFIRMED(10); // 已确认(已被接收)


    private int value;

    private MqMsgBatchStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static MqMsgBatchStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 5:
                return SENDED;
            case 10:
                return CONFIRMED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
