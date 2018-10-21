package com.jumbo.wms.model.vmi.Default;

public enum VmiCfgOrderCodeType {
    RTW(10), // 退仓
    TFX(15), // 转店
    ADJ(20);// 调整
    private int value;

    private VmiCfgOrderCodeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VmiCfgOrderCodeType valueOf(int value) {
        switch (value) {
            case 10:
                return RTW;
            case 15:
                return TFX;
            case 20:
                return ADJ;
            default:
                throw new IllegalArgumentException();
        }
    }
}
