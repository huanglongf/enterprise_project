package com.jumbo.wms.model.warehouse;


/**
 * 仓库状态 bin.hu
 * 
 * @author jumbo
 * 
 */
public enum WhAddStatusMode {
    PRINT(21), // 待打印
    DIEKING(24), // 待拣货
    SEPARATION(26), // 待分拣
    CHECK(29); // 待核对

    private int value;

    private WhAddStatusMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WhAddStatusMode valueOf(int value) {
        switch (value) {
            case 21:
                return PRINT;
            case 24:
                return DIEKING;
            case 26:
                return SEPARATION;
            case 29:
                return CHECK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
