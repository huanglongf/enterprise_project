package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.Date;


public class ESPNorSendSoCommand {

    private Long soId;
    private String code;
    private Date time;
    private Date tbCreateTime;
    private BigDecimal totalActual;
    private BigDecimal totalPointUsed;
    private BigDecimal gcAmt;
    private BigDecimal listRetail;
    private BigDecimal netRetail;
    private String skuCode;
    private BigDecimal qty;
    
    public Long getSoId() {
        return soId;
    }
    public void setSoId(Long soId) {
        this.soId = soId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public Date getTbCreateTime() {
        return tbCreateTime;
    }
    public void setTbCreateTime(Date tbCreateTime) {
        this.tbCreateTime = tbCreateTime;
    }
    public BigDecimal getTotalActual() {
        return totalActual;
    }
    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }
    public BigDecimal getTotalPointUsed() {
        return totalPointUsed;
    }
    public void setTotalPointUsed(BigDecimal totalPointUsed) {
        this.totalPointUsed = totalPointUsed;
    }
    public BigDecimal getGcAmt() {
        return gcAmt;
    }
    public void setGcAmt(BigDecimal gcAmt) {
        this.gcAmt = gcAmt;
    }
    public BigDecimal getListRetail() {
        return listRetail;
    }
    public void setListRetail(BigDecimal listRetail) {
        this.listRetail = listRetail;
    }
    public BigDecimal getNetRetail() {
        return netRetail;
    }
    public void setNetRetail(BigDecimal netRetail) {
        this.netRetail = netRetail;
    }
    public String getSkuCode() {
        return skuCode;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    public BigDecimal getQty() {
        return qty;
    }
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
    
}
