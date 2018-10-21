package com.bt.lmis.balance.model;

import java.math.BigDecimal;
import java.util.Date;

public class StorageDataEstimate {
    private Integer id;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer contractId;

    private Integer fixedQty;

    private String fixedUnit;

    private BigDecimal fixedCost;

    private String fixedComment;

    private Integer trayQty;

    private String trayQtyunit;

    private BigDecimal trayCost;

    private String trayComment;

    private BigDecimal areaQty;

    private String areaCostunit;

    private BigDecimal areaCost;

    private String areaComment;

    private BigDecimal pieceQty;

    private String pieceUnit;

    private BigDecimal pieceCost;

    private String pieceComment;

    private String batchNumber;
    
    private Integer sscId;

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

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getFixedQty() {
        return fixedQty;
    }

    public void setFixedQty(Integer fixedQty) {
        this.fixedQty = fixedQty;
    }

    public String getFixedUnit() {
        return fixedUnit;
    }

    public void setFixedUnit(String fixedUnit) {
        this.fixedUnit = fixedUnit == null ? null : fixedUnit.trim();
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(BigDecimal fixedCost) {
        this.fixedCost = fixedCost;
    }

    public String getFixedComment() {
        return fixedComment;
    }

    public void setFixedComment(String fixedComment) {
        this.fixedComment = fixedComment == null ? null : fixedComment.trim();
    }

    public Integer getTrayQty() {
        return trayQty;
    }

    public void setTrayQty(Integer trayQty) {
        this.trayQty = trayQty;
    }

    public String getTrayQtyunit() {
        return trayQtyunit;
    }

    public void setTrayQtyunit(String trayQtyunit) {
        this.trayQtyunit = trayQtyunit == null ? null : trayQtyunit.trim();
    }

    public BigDecimal getTrayCost() {
        return trayCost;
    }

    public void setTrayCost(BigDecimal trayCost) {
        this.trayCost = trayCost;
    }

    public String getTrayComment() {
        return trayComment;
    }

    public void setTrayComment(String trayComment) {
        this.trayComment = trayComment == null ? null : trayComment.trim();
    }

    public BigDecimal getAreaQty() {
        return areaQty;
    }

    public void setAreaQty(BigDecimal areaQty) {
        this.areaQty = areaQty;
    }

    public String getAreaCostunit() {
        return areaCostunit;
    }

    public void setAreaCostunit(String areaCostunit) {
        this.areaCostunit = areaCostunit == null ? null : areaCostunit.trim();
    }

    public BigDecimal getAreaCost() {
        return areaCost;
    }

    public void setAreaCost(BigDecimal areaCost) {
        this.areaCost = areaCost;
    }

    public String getAreaComment() {
        return areaComment;
    }

    public void setAreaComment(String areaComment) {
        this.areaComment = areaComment == null ? null : areaComment.trim();
    }

    public BigDecimal getPieceQty() {
        return pieceQty;
    }

    public void setPieceQty(BigDecimal pieceQty) {
        this.pieceQty = pieceQty;
    }

    public String getPieceUnit() {
        return pieceUnit;
    }

    public void setPieceUnit(String pieceUnit) {
        this.pieceUnit = pieceUnit == null ? null : pieceUnit.trim();
    }

    public BigDecimal getPieceCost() {
        return pieceCost;
    }

    public void setPieceCost(BigDecimal pieceCost) {
        this.pieceCost = pieceCost;
    }

    public String getPieceComment() {
        return pieceComment;
    }

    public void setPieceComment(String pieceComment) {
        this.pieceComment = pieceComment == null ? null : pieceComment.trim();
    }

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Integer getSscId() {
		return sscId;
	}

	public void setSscId(Integer sscId) {
		this.sscId = sscId;
	}

    
}