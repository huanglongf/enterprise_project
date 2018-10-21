package com.jumbo.wms.model.warehouse;

public enum PaymentType {
    CASH_PAY(10), // 现金支付
    ONLINE_PAY(20), // 在线支付
    INTEGRATION_PAY(30), // 积分抵扣
    BALANCE_PAY(40), // 余额支付
    COUPON_PAY(50), // 优惠券金额
    GIFTCARD_PAY(60), // 礼品卡金额
    OTHERWAY_PAY(90), // 其他支付
    PACKAGING_PAY(100);// 包装费
    private int value;

    private PaymentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentType valueOf(int value) {
        switch (value) {
            case 10:
                return CASH_PAY;
            case 20:
                return ONLINE_PAY;
            case 30:
                return INTEGRATION_PAY;
            case 40:
                return BALANCE_PAY;
            case 50:
                return COUPON_PAY;
            case 60:
                return GIFTCARD_PAY;
            case 90:
                return OTHERWAY_PAY;
            case 100:
                return PACKAGING_PAY;
            default:
                return null;
        }
    }
}
