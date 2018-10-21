package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * WMS通用出发票明细信息
 *
 */
public class WmsOutBoundInvoiceLine implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6835046073526560116L;
    /**
     * 类别
     */
    private String item;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 合计
     */
    private BigDecimal amt;
    /**
     * 行号
     */
    private String lineNO;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getLineNO() {
        return lineNO;
    }

    public void setLineNO(String lineNO) {
        this.lineNO = lineNO;
    }


}
