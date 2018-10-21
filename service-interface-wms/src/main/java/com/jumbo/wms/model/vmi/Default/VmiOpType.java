package com.jumbo.wms.model.vmi.Default;

public enum VmiOpType {
    RTW(1), // 退大仓
    TFX(2), // 转店退仓
    ADJ(3), // 调整
    TFXRSN(4);// 转店
    private int value;

    private VmiOpType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VmiOpType valueOf(int value) {
        switch (value) {
            case 1:
                return RTW;
            case 2:
                return TFX;
            case 3:
                return ADJ;
            case 4:
                return TFXRSN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
