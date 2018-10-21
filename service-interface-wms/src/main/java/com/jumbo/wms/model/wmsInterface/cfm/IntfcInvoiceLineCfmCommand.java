package com.jumbo.wms.model.wmsInterface.cfm;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;

/**
 * 通用反馈发票明细
 *
 */
public class IntfcInvoiceLineCfmCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7425050545287408711L;
    /**
     * ID
     */
    private Long id;
    /**
     * 通用反馈发票头ID
     */
    private IntfcInvoiceCfm intfcInvoiceId;
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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public IntfcInvoiceCfm getIntfcInvoiceId() {
        return intfcInvoiceId;
    }
    public void setIntfcInvoiceId(IntfcInvoiceCfm intfcInvoiceId) {
        this.intfcInvoiceId = intfcInvoiceId;
    }
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
