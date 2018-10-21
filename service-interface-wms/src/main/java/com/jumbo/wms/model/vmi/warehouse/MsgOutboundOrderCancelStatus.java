package com.jumbo.wms.model.vmi.warehouse;


public enum MsgOutboundOrderCancelStatus {
    OMS_LOSE(-1),//OMS接口取消失败
    CANCELED(0), // 取消成功，待WMS处理
    CREATED(1), // 新建
    SENT(2), // 已发送的
    FINISHED(10), // 处理完成
    INEXECUTION(20), // 不执行
    UNKNOWN(30); // 待查询


    private int value;

    private MsgOutboundOrderCancelStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static MsgOutboundOrderCancelStatus valueOf(int value) {
        switch (value) {
            case -1:
                return OMS_LOSE;
            case 0:
                return CANCELED;
            case 1:
                return CREATED;
            case 2:
                return SENT;
            case 10:
                return FINISHED;
            case 20:
                return INEXECUTION;
            case 30:
                return UNKNOWN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
