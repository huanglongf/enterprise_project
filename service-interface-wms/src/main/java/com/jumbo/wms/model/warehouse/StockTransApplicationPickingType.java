package com.jumbo.wms.model.warehouse;

/**
 * 作业单配货类型
 * 
 * @author jinlong.ke
 * 
 */
public enum StockTransApplicationPickingType {
    SKU_SINGLE(1), // 单件
    SKU_MANY(10), // 多件
    GROUP(20), // 团购
    SECKILL(30), // 秒杀
    SKU_PACKAGE(2), // 套装组合
    O2OANDQS(3)// O2OANDQS
    ;
    private int value;

    private StockTransApplicationPickingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockTransApplicationPickingType valueOf(int value) {
        switch (value) {
            case 1:
                return SKU_SINGLE;
            case 10:
                return SKU_MANY;
            case 20:
                return GROUP;
            case 30:
                return SECKILL;
            case 2:
                return SKU_PACKAGE;
            case 3:
                return O2OANDQS;
            default:
                return null;
                // throw new IllegalArgumentException();
        }
    }
}
