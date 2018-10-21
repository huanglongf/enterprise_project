package com.jumbo.wms.model.warehouse;

/**
 * 特殊处理商品类型枚举
 * 
 * @author jinlong.ke
 * 
 */
public enum SpecialSkuType {
    NORMAL(1),//需特殊处理核对
    FILL_PACKAGE(2);//包裹填充物
    private int value;
    private SpecialSkuType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static SpecialSkuType valueOf(int value){
        switch(value){
            case 1:
                return NORMAL;
            case 2:
                return FILL_PACKAGE;
            default:
                return null;
        }
    }
}
