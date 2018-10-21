package com.jumbo.wms.model.vmi.Default;

public enum RsnType {
    TYPEONE(1), // 按计划
    TYPETWO(2);// 按实际收货
    private int value;

    private RsnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RsnType valueOf(int value) {
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
