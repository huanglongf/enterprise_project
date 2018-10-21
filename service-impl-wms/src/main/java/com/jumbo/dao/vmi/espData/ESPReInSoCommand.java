package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.Date;


public class ESPReInSoCommand {

    private Long requestId;
    private String raCode;
    private Long raId;
    private Date inboundTime;
    private Date createTime;
    private Long oldSoId;
    private BigDecimal oldSoTotalActual;
    private BigDecimal oldSoPointUsed;
    private BigDecimal oldListRetail;
    private BigDecimal oldNetRetail;
    private String oldSkuCode;
    private String extensionCode;
    private BigDecimal oldQty;
    private Long newSoId;


    public Long getOldSoId() {
        return oldSoId;
    }

    public void setOldSoId(Long oldSoId) {
        this.oldSoId = oldSoId;
    }

    public BigDecimal getOldSoTotalActual() {
        return oldSoTotalActual;
    }

    public void setOldSoTotalActual(BigDecimal oldSoTotalActual) {
        this.oldSoTotalActual = oldSoTotalActual;
    }

    public BigDecimal getOldSoPointUsed() {
        return oldSoPointUsed;
    }

    public void setOldSoPointUsed(BigDecimal oldSoPointUsed) {
        this.oldSoPointUsed = oldSoPointUsed;
    }

    public BigDecimal getOldListRetail() {
        return oldListRetail;
    }

    public void setOldListRetail(BigDecimal oldListRetail) {
        this.oldListRetail = oldListRetail;
    }

    public BigDecimal getOldNetRetail() {
        return oldNetRetail;
    }

    public void setOldNetRetail(BigDecimal oldNetRetail) {
        this.oldNetRetail = oldNetRetail;
    }

    public BigDecimal getOldQty() {
        return oldQty;
    }

    public void setOldQty(BigDecimal oldQty) {
        this.oldQty = oldQty;
    }

    public Long getNewSoId() {
        return newSoId;
    }

    public void setNewSoId(Long newSoId) {
        this.newSoId = newSoId;
    }

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

}
