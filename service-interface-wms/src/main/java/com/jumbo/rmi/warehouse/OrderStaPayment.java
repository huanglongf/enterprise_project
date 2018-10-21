package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author zaibiao.li
 * 
 */

public class OrderStaPayment implements Serializable {

    private static final long serialVersionUID = -2121942496778108421L;
    /*
     * 金额
     */
    private BigDecimal amt;
    /*
     * 类型(10:现金支付 20:在线支付 30:积分抵扣 40:余额支付 50: 优惠券金额 60:礼品卡金额 90:其他支付 100:包装费)
     */
    private int type;
    /*
     * 支付说明
     */
    private String memo;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
