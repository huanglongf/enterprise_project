package com.jumbo.wms.model;

public enum WmsOtherOutBoundInvNoticeOmsStatus {
    OTHER_INBOUND(1), // 其他入库
    OTHER_OUTBOUND(2), // 其他出库
    VMI_ADJUSTMENT(3), // VMI库存调整
    VMI_OWNER_TRANSFER(4);//转店

    private int value;

    private WmsOtherOutBoundInvNoticeOmsStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static WmsOtherOutBoundInvNoticeOmsStatus valueOf(int value) {
        switch (value) {
            case 1:
                return OTHER_INBOUND;
            case 2:
                return OTHER_OUTBOUND;
            case 3:
                return VMI_ADJUSTMENT;
            case 4:
                return VMI_OWNER_TRANSFER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
