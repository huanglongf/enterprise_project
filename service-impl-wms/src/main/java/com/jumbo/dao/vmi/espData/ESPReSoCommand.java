package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.Date;


public class ESPReSoCommand {

    private Long requestId;
    private String soCode;
    private String raCode;
    private Long raId;
    private Long soLineId;
    private Date inboundTime;
    private Date outboundTime;
    private Date createTime;
    private BigDecimal soTotalActual;
    private BigDecimal soPointUsed;
    private BigDecimal listRetail;
    private BigDecimal netRetail;
    private String oldSkuCode;
    private String newSkuCode;
    private String extensionCode;
    private BigDecimal qty;
    private Long soId;
    private BigDecimal gcAmt;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRaCode() {
        return raCode;
    }

    public void setRaCode(String raCode) {
        this.raCode = raCode;
    }

    public Long getRaId() {
        return raId;
    }

    public void setRaId(Long raId) {
        this.raId = raId;
    }

    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public BigDecimal getSoTotalActual() {
        return soTotalActual;
    }

    public void setSoTotalActual(BigDecimal soTotalActual) {
        this.soTotalActual = soTotalActual;
    }

    public BigDecimal getSoPointUsed() {
        return soPointUsed;
    }

    public void setSoPointUsed(BigDecimal soPointUsed) {
        this.soPointUsed = soPointUsed;
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


    public String getOldSkuCode() {
		return oldSkuCode;
	}

	public void setOldSkuCode(String oldSkuCode) {
		this.oldSkuCode = oldSkuCode;
	}

	public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public BigDecimal getGcAmt() {
        return gcAmt;
    }

    public void setGcAmt(BigDecimal gcAmt) {
        this.gcAmt = gcAmt;
    }

    public Long getSoLineId() {
        return soLineId;
    }

    public void setSoLineId(Long soLineId) {
        this.soLineId = soLineId;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

	public String getNewSkuCode() {
		return newSkuCode;
	}

	public void setNewSkuCode(String newSkuCode) {
		this.newSkuCode = newSkuCode;
	}

}
