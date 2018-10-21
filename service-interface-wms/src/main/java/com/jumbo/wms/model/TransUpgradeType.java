package com.jumbo.wms.model;

public enum TransUpgradeType {
    STORE_REQUIREMENET(1), WMS_DELAY(2);
    private int value;

    private TransUpgradeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransUpgradeType valueOf(int value) {
        switch (value) {
            case 1:
                return STORE_REQUIREMENET;
            default:
                return WMS_DELAY;
        }
    }
}
