package com.jumbo.wms.model.vmi.Default;

public enum AsnOrderType {
    TYPEONE(1), // 按单收货
    TYPETWO(2);// 按箱收货
    private int value;

    private AsnOrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AsnOrderType valueOf(int value) {
        switch (value) {
            case 1:
                return TYPEONE;
            case 2:
                return TYPETWO;
            default:
                throw new IllegalArgumentException();
        }
    }
}
