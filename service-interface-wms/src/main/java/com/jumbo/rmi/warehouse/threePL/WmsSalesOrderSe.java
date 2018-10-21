package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 出库单整单礼品明细
 * 
 */
public class WmsSalesOrderSe implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3585494567694175325L;

    /**
     * 类型 8:Nike HK 3/8礼品卡打印
     */
    private Integer type;

    /**
     * 备注(如打印类型则为打印内容)
     */
    private String memo;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
