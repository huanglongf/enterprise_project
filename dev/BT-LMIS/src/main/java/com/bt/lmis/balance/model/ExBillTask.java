package com.bt.lmis.balance.model;

import java.util.Date;

/** 
 * @ClassName: ExBillTak
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年11月25日 下午2:49:47 
 * 
 */
public class ExBillTask {
	private String id;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private Boolean isEnabled;
	private Integer contractId;
	private String billYm;
	private String billCycleStart;
	private String billCycleEnd;
	
	public ExBillTask() {}
	
	public ExBillTask(String billYm, String billCycleStart, String billCycleEnd) {
		this.billYm = billYm;
		this.billCycleStart = billCycleStart;
		this.billCycleEnd = billCycleEnd;
		
	}
	
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
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public String getBillYm() {
		return billYm;
	}

	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}

	public String getBillCycleStart() {
		return billCycleStart;
	}
	public void setBillCycleStart(String billCycleStart) {
		this.billCycleStart = billCycleStart;
	}
	public String getBillCycleEnd() {
		return billCycleEnd;
	}
	public void setBillCycleEnd(String billCycleEnd) {
		this.billCycleEnd = billCycleEnd;
	}

}
