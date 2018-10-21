package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.Date;

public class ESPChangeOutCommand {
	 private Long requestId;
	 private String code;
	 /**so_id*/
	 private Long newId;
	 /**outboundTime*/
	 private Date time;
	 private Date tbCreateTime;
	 private BigDecimal newSoTotalActual;
	 private BigDecimal newSoPointUsed;
	 private BigDecimal gcAmt;
	 private BigDecimal newListRetail;
	 private BigDecimal newNetRetail;
	 private String newSkuCode;
	 private String extensionCode;
	 private BigDecimal newQty;
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getNewId() {
		return newId;
	}
	public void setNewId(Long newId) {
		this.newId = newId;
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
	public BigDecimal getNewSoTotalActual() {
		return newSoTotalActual;
	}
	public void setNewSoTotalActual(BigDecimal newSoTotalActual) {
		this.newSoTotalActual = newSoTotalActual;
	}
	public BigDecimal getNewSoPointUsed() {
		return newSoPointUsed;
	}
	public void setNewSoPointUsed(BigDecimal newSoPointUsed) {
		this.newSoPointUsed = newSoPointUsed;
	}
	public BigDecimal getGcAmt() {
		return gcAmt;
	}
	public void setGcAmt(BigDecimal gcAmt) {
		this.gcAmt = gcAmt;
	}
	public BigDecimal getNewListRetail() {
		return newListRetail;
	}
	public void setNewListRetail(BigDecimal newListRetail) {
		this.newListRetail = newListRetail;
	}
	public BigDecimal getNewNetRetail() {
		return newNetRetail;
	}
	public void setNewNetRetail(BigDecimal newNetRetail) {
		this.newNetRetail = newNetRetail;
	}
	public String getNewSkuCode() {
		return newSkuCode;
	}
	public void setNewSkuCode(String newSkuCode) {
		this.newSkuCode = newSkuCode;
	}
	public String getExtensionCode() {
		return extensionCode;
	}
	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}
	public BigDecimal getNewQty() {
		return newQty;
	}
	public void setNewQty(BigDecimal newQty) {
		this.newQty = newQty;
	}
	 
	 
}
