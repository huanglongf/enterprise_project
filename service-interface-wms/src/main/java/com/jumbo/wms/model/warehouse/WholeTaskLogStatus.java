package com.jumbo.wms.model.warehouse;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public enum WholeTaskLogStatus {
    ERROR(-1), // 错误
    NEW(1), // 新建
    EXECUTING(5), // 执行中
    FINISH(10); // 完成

    private int value;

    private WholeTaskLogStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WholeTaskLogStatus valueOf(int value) {
        switch (value) {
            case -1:
                return ERROR;
            case 1:
                return NEW;
            case 5:
                return EXECUTING;
            case 10:
                return FINISH;
            default:
                throw new IllegalArgumentException();
        }
    }
}
