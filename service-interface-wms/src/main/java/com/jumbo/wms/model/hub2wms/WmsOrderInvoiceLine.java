package com.jumbo.wms.model.hub2wms;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * HUB2WMS过仓 发票明细
 * 
 * @author jinlong.ke
 * 
 */
public class WmsOrderInvoiceLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1067713867448208966L;
    /*
     * 商品
     */
    private String item;
    /*
     * 数量
     */
    private Long qty;
    /*
     * 单价
     */
    private BigDecimal unitPrice;
    /*
     * 总金额
     */
    private BigDecimal amt;
    /*
     * 平台行号 
     */
    private String lineNo;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }


}
