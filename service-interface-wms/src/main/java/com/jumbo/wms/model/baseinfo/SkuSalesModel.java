package com.jumbo.wms.model.baseinfo;


public enum SkuSalesModel {
    PAYMENT(0), // 付款经销
    CONSIGNMENT(1), // 代销
    SETTLEMENT(2), // 结算经销
    SETTLEMENT_OR_CONSIGNMENT(3);

    private int value;

    private SkuSalesModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkuSalesModel valueOf(int value) {
        switch (value) {
            case 0:
                return PAYMENT;
            case 1:
                return CONSIGNMENT;
            case 2:
                return SETTLEMENT;
            case 3:
                return CONSIGNMENT;
            default:
                return CONSIGNMENT;
        }
    }
}
