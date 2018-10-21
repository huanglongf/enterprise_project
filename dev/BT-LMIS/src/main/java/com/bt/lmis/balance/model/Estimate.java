package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: Estimate
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月24日 下午2:21:10 
 * 
 */
public class Estimate implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 5117195454440475713L;
	private String id;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private String batchNumber;
	private String batchStatus;
	private int rank;
	private String domainFrom;
	private String domainTo;
	private int estimateType;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getDomainFrom() {
		return domainFrom;
	}
	public void setDomainFrom(String domainFrom) {
		this.domainFrom = domainFrom;
	}
	public String getDomainTo() {
		return domainTo;
	}
	public void setDomainTo(String domainTo) {
		this.domainTo = domainTo;
	}
	public int getEstimateType() {
		return estimateType;
	}
	public void setEstimateType(int estimateType) {
		this.estimateType = estimateType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}