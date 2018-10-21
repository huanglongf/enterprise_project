package com.bt.lmis.balance.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * bal_operationfee_data_daily_settlement_estimate
 * 仓库操作数据预估每日汇总
 * @author jindong.lin
 *
 */
public class OperationfeeDataDailySettlementEstimate {
    private Integer id;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String batchNumber;

    private Integer contractId;

    private BigDecimal btcQty;

    private String btcQtyunit;

    private BigDecimal btcFee;

    private String btcRemark;

    private BigDecimal btbQty;

    private String btbQtyunit;

    private BigDecimal btbFee;

    private String btbRemark;

    private BigDecimal returnQty;

    private String returnQtyunit;

    private BigDecimal returnFee;

    private String returnRemark;

    private BigDecimal ibQty;

    private String ibQtyunit;

    private BigDecimal ibFee;

    private String ibRemark;

    private BigDecimal xseQty;

    private String xseQtyunit;

    private BigDecimal xseFee;

    private String xseRemark;

    private BigDecimal gdQty;

    private String gdQtyunit;

    private BigDecimal gdFee;

    private String gdRemark;
    
    private Integer firstResult;
    private Integer maxResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber == null ? null : batchNumber.trim();
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getBtcQty() {
        return btcQty;
    }

    public void setBtcQty(BigDecimal btcQty) {
        this.btcQty = btcQty;
    }

    public String getBtcQtyunit() {
        return btcQtyunit;
    }

    public void setBtcQtyunit(String btcQtyunit) {
        this.btcQtyunit = btcQtyunit == null ? null : btcQtyunit.trim();
    }

    public BigDecimal getBtcFee() {
        return btcFee;
    }

    public void setBtcFee(BigDecimal btcFee) {
        this.btcFee = btcFee;
    }

    public String getBtcRemark() {
        return btcRemark;
    }

    public void setBtcRemark(String btcRemark) {
        this.btcRemark = btcRemark == null ? null : btcRemark.trim();
    }

    public BigDecimal getBtbQty() {
        return btbQty;
    }

    public void setBtbQty(BigDecimal btbQty) {
        this.btbQty = btbQty;
    }

    public String getBtbQtyunit() {
        return btbQtyunit;
    }

    public void setBtbQtyunit(String btbQtyunit) {
        this.btbQtyunit = btbQtyunit == null ? null : btbQtyunit.trim();
    }

    public BigDecimal getBtbFee() {
        return btbFee;
    }

    public void setBtbFee(BigDecimal btbFee) {
        this.btbFee = btbFee;
    }

    public String getBtbRemark() {
        return btbRemark;
    }

    public void setBtbRemark(String btbRemark) {
        this.btbRemark = btbRemark == null ? null : btbRemark.trim();
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public String getReturnQtyunit() {
        return returnQtyunit;
    }

    public void setReturnQtyunit(String returnQtyunit) {
        this.returnQtyunit = returnQtyunit == null ? null : returnQtyunit.trim();
    }

    public BigDecimal getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(BigDecimal returnFee) {
        this.returnFee = returnFee;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark == null ? null : returnRemark.trim();
    }

    public BigDecimal getIbQty() {
        return ibQty;
    }

    public void setIbQty(BigDecimal ibQty) {
        this.ibQty = ibQty;
    }

    public String getIbQtyunit() {
        return ibQtyunit;
    }

    public void setIbQtyunit(String ibQtyunit) {
        this.ibQtyunit = ibQtyunit == null ? null : ibQtyunit.trim();
    }

    public BigDecimal getIbFee() {
        return ibFee;
    }

    public void setIbFee(BigDecimal ibFee) {
        this.ibFee = ibFee;
    }

    public String getIbRemark() {
        return ibRemark;
    }

    public void setIbRemark(String ibRemark) {
        this.ibRemark = ibRemark == null ? null : ibRemark.trim();
    }

    public BigDecimal getXseQty() {
        return xseQty;
    }

    public void setXseQty(BigDecimal xseQty) {
        this.xseQty = xseQty;
    }

    public String getXseQtyunit() {
        return xseQtyunit;
    }

    public void setXseQtyunit(String xseQtyunit) {
        this.xseQtyunit = xseQtyunit == null ? null : xseQtyunit.trim();
    }

    public BigDecimal getXseFee() {
        return xseFee;
    }

    public void setXseFee(BigDecimal xseFee) {
        this.xseFee = xseFee;
    }

    public String getXseRemark() {
        return xseRemark;
    }

    public void setXseRemark(String xseRemark) {
        this.xseRemark = xseRemark == null ? null : xseRemark.trim();
    }

    public BigDecimal getGdQty() {
        return gdQty;
    }

    public void setGdQty(BigDecimal gdQty) {
        this.gdQty = gdQty;
    }

    public String getGdQtyunit() {
        return gdQtyunit;
    }

    public void setGdQtyunit(String gdQtyunit) {
        this.gdQtyunit = gdQtyunit == null ? null : gdQtyunit.trim();
    }

    public BigDecimal getGdFee() {
        return gdFee;
    }

    public void setGdFee(BigDecimal gdFee) {
        this.gdFee = gdFee;
    }

    public String getGdRemark() {
        return gdRemark;
    }

    public void setGdRemark(String gdRemark) {
        this.gdRemark = gdRemark == null ? null : gdRemark.trim();
    }

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
}