package com.jumbo.wms.model.baseinfo;

public enum SkuSnCheckMode {

    GENERAL(1), // general普通
    STB_SVC(3), //
    STB_MSR(5), //
    STB_SVCQ(7), //
    NIKE(8), //
    STB_BEN(9);// 星巴克 本

    private int value;

    private SkuSnCheckMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkuSnCheckMode valueOf(int value) {
        switch (value) {
            case 1:
                return GENERAL;
            case 3:
                return STB_SVC;
            case 5:
                return STB_MSR;
            case 7:
                return STB_SVCQ;
            case 8:
                return NIKE;
            case 9:
                return STB_BEN;
            default:
                return GENERAL;
        }
    }
}
