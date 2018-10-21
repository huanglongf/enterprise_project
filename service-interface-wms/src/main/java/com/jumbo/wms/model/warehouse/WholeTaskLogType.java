package com.jumbo.wms.model.warehouse;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public enum WholeTaskLogType {
    INV_FILE_UPLOAD(1); // 库存文件上传

    private int value;

    private WholeTaskLogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WholeTaskLogType valueOf(int value) {
        switch (value) {
            case 1:
                return INV_FILE_UPLOAD;
            default:
                throw new IllegalArgumentException();
        }
    }
}
