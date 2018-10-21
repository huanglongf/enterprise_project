package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

public class ManagementOtherLadder {
    private Integer id;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer contractId;

    private String bntInterval;

    private String bntIntervalUnit;
    
    private String ladderMethod;
    
    private String ladderMethodName;

    private BigDecimal rate;

    private String type;
    
    private String typeName;

    private Integer sort;

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

    public String getBntInterval() {
        return bntInterval;
    }

    public void setBntInterval(String bntInterval) {
        this.bntInterval = bntInterval == null ? null : bntInterval.trim();
    }

    public String getBntIntervalUnit() {
        return bntIntervalUnit;
    }

    public void setBntIntervalUnit(String bntIntervalUnit) {
        this.bntIntervalUnit = bntIntervalUnit == null ? null : bntIntervalUnit.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName =  typeName == null ? null : typeName.trim();
	}

	public String getLadderMethod() {
		return ladderMethod;
	}

	public void setLadderMethod(String ladderMethod) {
		this.ladderMethod = ladderMethod == null ? null : ladderMethod.trim();
	}

	public String getLadderMethodName() {
		return ladderMethodName;
	}

	public void setLadderMethodName(String ladderMethodName) {
		this.ladderMethodName = ladderMethodName == null ? null : ladderMethodName.trim();
	}
}