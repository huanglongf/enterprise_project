package com.jumbo.wms.model.baseinfo;

public enum SkuSnCheckCfgType {

    REGULAR(1), // regular正则校验
    CHAR_REPLACE(50), // char replace 字符替换
    MAPPING(999);// mapping

    private int value;

    private SkuSnCheckCfgType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkuSnCheckCfgType valueOf(int value) {
        switch (value) {
            case 1:
                return REGULAR;
            case 50:
                return CHAR_REPLACE;
            case 999:
                return MAPPING;
            default:
                return REGULAR;
        }
    }
}
