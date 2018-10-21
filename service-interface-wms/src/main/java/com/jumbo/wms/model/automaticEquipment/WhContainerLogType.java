package com.jumbo.wms.model.automaticEquipment;

public enum WhContainerLogType {
    SINGLE_BOX(1), // 单件
    MANY_BOX(2);// 多件
    private int value;

    private WhContainerLogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WhContainerLogType valueOf(int value) {
        switch (value) {
            case 1:
                return SINGLE_BOX;
            case 2:
                return MANY_BOX;
            default:
                return null;
        }
    }
}
