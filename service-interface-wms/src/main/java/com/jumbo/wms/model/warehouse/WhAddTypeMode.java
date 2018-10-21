package com.jumbo.wms.model.warehouse;


/**
 * 仓库状态 bin.hu
 * 
 * @author jumbo
 * 
 */
public enum WhAddTypeMode {
    PICKINGLIST(1); // 仓储附属状态类型

    private int value;

    private WhAddTypeMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WhAddTypeMode valueOf(int value) {
        switch (value) {
            case 1:
                return PICKINGLIST;
            default:
                return null;
        }
    }
}
