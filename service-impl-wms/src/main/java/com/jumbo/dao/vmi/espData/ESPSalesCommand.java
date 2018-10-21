package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.Date;

public class ESPSalesCommand {

    private Long soId;
    private String code;
    private Date createTime;
    private BigDecimal totalActual;// 正常销售
    private BigDecimal totalPointUsed = new BigDecimal(0);
    private BigDecimal gcAmt;
    private BigDecimal listRetail;
    private BigDecimal netRetail;
    private BigDecimal qty;
    private String skuCode;
    private Date time; // 作为排序依据

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



}
