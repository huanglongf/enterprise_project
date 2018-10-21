package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 出库单指定商品行礼品相关信息
 * 
 */
public class WmsSalesOrderGiftLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3585494567694175325L;

    /**
     * 礼品类型 1,赠送礼品卡; 3,商品特殊印制; 10,商品特殊包装; 20,Coach保修卡 60,哈根达斯券
     */
    private Integer type;

    /**
     * 备注
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
