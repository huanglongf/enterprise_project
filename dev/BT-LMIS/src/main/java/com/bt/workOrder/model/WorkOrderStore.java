package com.bt.workOrder.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: WorkOrderStore
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月24日 下午5:39:48 
 * 
 */
public class WorkOrderStore implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -7247050014634923047L;
	
	private String id;
	private Date createTime;
	private String createBy;
	private String createByDisplay;
	private String createByGroup;
	private String createByGroupDisplay;
	private Date updateTime;
	private String updateBy;
	private String woNo;
	private Integer woStatus;
	private Integer submitFlag;
	private String woType;
	private String woTypeDisplay;
	private String errorType;
	private String errorTypeDisplay;
	private String issueDescription;
	private String platformNumber;
	private String waybill;
	private String currentProcessor;
	private String currentProcessorDisplay;
	private String currentProcessorGroup;
	private String currentProcessorGroupDisplay;
	private Date lastProcessTime;
	private String lastProcessInfo;
	private Date overTime;
	private String accessory;
	private int version;
	//
	private Integer followUpFlag;//跟进状态
	private String owner;
	private String ownerDisplay;
	private String ownerGroup;
	private String ownerGroupDisplay;
	
	private Integer errorFlag; //是否异常工单 0-否 1-是
	private Integer processDepartment;//处理部门 0-物流部 1-销售运营部
	private String relatedNumber;//相关单据号
	private String problemStore;
	private String problemStoreDisplay;
	private Date expectationProcessTime;
	private String operationSystem;
	private String title;
	private String woTypeRe;
	private String woTypeReDisplay;
	
	private String relatedWoNo;
	private String splitTo;
	
	public String getSplitTo() {
		return splitTo;
	}
	public void setSplitTo(String splitTo) {
		this.splitTo = splitTo;
	}
	public String getRelatedWoNo() {
		return relatedWoNo;
	}
	public void setRelatedWoNo(String relatedWoNo) {
		this.relatedWoNo = relatedWoNo;
	}
	public WorkOrderStore() {}
	public WorkOrderStore(String id) {
		this.id = id;
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
	public String getCreateByDisplay() {
		return createByDisplay;
	}
	public void setCreateByDisplay(String createByDisplay) {
		this.createByDisplay = createByDisplay;
	}
	public String getCreateByGroup() {
		return createByGroup;
	}
	public void setCreateByGroup(String createByGroup) {
		this.createByGroup = createByGroup;
	}
	public String getCreateByGroupDisplay() {
		return createByGroupDisplay;
	}
	public void setCreateByGroupDisplay(String createByGroupDisplay) {
		this.createByGroupDisplay = createByGroupDisplay;
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
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public Integer getWoStatus() {
		return woStatus;
	}
	public void setWoStatus(Integer woStatus) {
		this.woStatus = woStatus;
	}
	public Integer getSubmitFlag() {
		return submitFlag;
	}
	public void setSubmitFlag(Integer submitFlag) {
		this.submitFlag = submitFlag;
	}
	public Integer getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(Integer followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	public Integer getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(Integer errorFlag) {
		this.errorFlag = errorFlag;
	}
	public Integer getProcessDepartment() {
		return processDepartment;
	}
	public void setProcessDepartment(Integer processDepartment) {
		this.processDepartment = processDepartment;
	}
	public String getWoType() {
		return woType;
	}
	public void setWoType(String woType) {
		this.woType = woType;
	}
	public String getWoTypeDisplay() {
		return woTypeDisplay;
	}
	public void setWoTypeDisplay(String woTypeDisplay) {
		this.woTypeDisplay = woTypeDisplay;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorTypeDisplay() {
		return errorTypeDisplay;
	}
	public void setErrorTypeDisplay(String errorTypeDisplay) {
		this.errorTypeDisplay = errorTypeDisplay;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public String getPlatformNumber() {
		return platformNumber;
	}
	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getRelatedNumber() {
		return relatedNumber;
	}
	public void setRelatedNumber(String relatedNumber) {
		this.relatedNumber = relatedNumber;
	}
	public String getProblemStore() {
		return problemStore;
	}
	public void setProblemStore(String problemStore) {
		this.problemStore = problemStore;
	}
	public Date getExpectationProcessTime() {
		return expectationProcessTime;
	}
	public void setExpectationProcessTime(Date expectationProcessTime) {
		this.expectationProcessTime = expectationProcessTime;
	}
	public String getOperationSystem() {
		return operationSystem;
	}
	public void setOperationSystem(String operationSystem) {
		this.operationSystem = operationSystem;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerDisplay() {
		return ownerDisplay;
	}
	public void setOwnerDisplay(String ownerDisplay) {
		this.ownerDisplay = ownerDisplay;
	}
	public String getOwnerGroup() {
		return ownerGroup;
	}
	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}
	public String getOwnerGroupDisplay() {
		return ownerGroupDisplay;
	}
	public void setOwnerGroupDisplay(String ownerGroupDisplay) {
		this.ownerGroupDisplay = ownerGroupDisplay;
	}
	public String getCurrentProcessor() {
		return currentProcessor;
	}
	public void setCurrentProcessor(String currentProcessor) {
		this.currentProcessor = currentProcessor;
	}
	public String getCurrentProcessorDisplay() {
		return currentProcessorDisplay;
	}
	public void setCurrentProcessorDisplay(String currentProcessorDisplay) {
		this.currentProcessorDisplay = currentProcessorDisplay;
	}
	public String getCurrentProcessorGroup() {
		return currentProcessorGroup;
	}
	public void setCurrentProcessorGroup(String currentProcessorGroup) {
		this.currentProcessorGroup = currentProcessorGroup;
	}
	public String getCurrentProcessorGroupDisplay() {
		return currentProcessorGroupDisplay;
	}
	public void setCurrentProcessorGroupDisplay(String currentProcessorGroupDisplay) {
		this.currentProcessorGroupDisplay = currentProcessorGroupDisplay;
	}
	public Date getLastProcessTime() {
		return lastProcessTime;
	}
	public void setLastProcessTime(Date lastProcessTime) {
		this.lastProcessTime = lastProcessTime;
	}
	public String getLastProcessInfo() {
		return lastProcessInfo;
	}
	public void setLastProcessInfo(String lastProcessInfo) {
		this.lastProcessInfo = lastProcessInfo;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	public String getWoTypeRe() {
		return woTypeRe;
	}
	public void setWoTypeRe(String woTypeRe) {
		this.woTypeRe = woTypeRe;
	}
	public String getWoTypeReDisplay() {
		return woTypeReDisplay;
	}
	public void setWoTypeReDisplay(String woTypeReDisplay) {
		this.woTypeReDisplay = woTypeReDisplay;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProblemStoreDisplay() {
		return problemStoreDisplay;
	}
	public void setProblemStoreDisplay(String problemStoreDisplay) {
		this.problemStoreDisplay = problemStoreDisplay;
	}
	@Override
	public String toString() {
		return "WorkOrderStore [id=" + id + ", createTime=" + createTime + ", createBy=" + createBy
				+ ", createByDisplay=" + createByDisplay + ", createByGroup=" + createByGroup
				+ ", createByGroupDisplay=" + createByGroupDisplay + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", woNo=" + woNo + ", woStatus=" + woStatus + ", submitFlag=" + submitFlag + ", woType="
				+ woType + ", woTypeDisplay=" + woTypeDisplay + ", errorType=" + errorType + ", errorTypeDisplay="
				+ errorTypeDisplay + ", issueDescription=" + issueDescription + ", platformNumber=" + platformNumber
				+ ", waybill=" + waybill + ", currentProcessor=" + currentProcessor + ", currentProcessorDisplay="
				+ currentProcessorDisplay + ", currentProcessorGroup=" + currentProcessorGroup
				+ ", currentProcessorGroupDisplay=" + currentProcessorGroupDisplay + ", lastProcessTime="
				+ lastProcessTime + ", lastProcessInfo=" + lastProcessInfo + ", overTime=" + overTime + ", accessory="
				+ accessory + ", version=" + version + ", followUpFlag=" + followUpFlag + ", owner=" + owner
				+ ", ownerDisplay=" + ownerDisplay + ", ownerGroup=" + ownerGroup + ", ownerGroupDisplay="
				+ ownerGroupDisplay + ", errorFlag=" + errorFlag + ", processDepartment=" + processDepartment
				+ ", relatedNumber=" + relatedNumber + ", problemStore=" + problemStore + ", problemStoreDisplay="
				+ problemStoreDisplay + ", expectationProcessTime=" + expectationProcessTime + ", operationSystem="
				+ operationSystem + ", title=" + title + ", woTypeRe=" + woTypeRe + ", woTypeReDisplay="
				+ woTypeReDisplay + ", relatedWoNo=" + relatedWoNo + ", splitTo=" + splitTo + "]";
	}
	
	
}
