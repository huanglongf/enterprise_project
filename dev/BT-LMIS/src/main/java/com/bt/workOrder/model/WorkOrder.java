package com.bt.workOrder.model;

import java.math.BigDecimal;

/**
 * @Title:WorkOrder
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月16日下午4:48:50
 */
public class WorkOrder {
	private String bySelf;
	private String id;
	private String createTime;
	private String createBy;
	private String createByDisplay;
	private String updateTime;
	private String updateBy;
	private String woNo;
	private String resourceWoNo;
	private String woSource;
	private String woAllocStatus;
	private String woAllocStatusDisplay;
	private String woProcessStatus;
	private String woProcessStatusDisplay;
	private String woType;
	private String woTypeDisplay;
	private String woLevel;
	private String woLevelDisplay;
	private String levelAlterReason;
	private String exceptionType;
	private String carriers;
	private String carriersDisplay;
	private String expressNumber;
	private String logisticsStatus;
	private String warningType;
	private String warningLevel;
	private String warehouses;
	private String warehousesDisplay;
	private String transportTime;
	private String stores;
	private String storesDisplay;
	private String platformNumber;
	private String relatedNumber;
	private String orderAmount;
	private String recipient;
	private String phone;
	private String address;
	private String queryPerson;
	private String allocatedBy;
	private String allocatedByDisplay;
	private String processor;
	private String processorDisplay;
	private String processContent;
	private  String supplementExplain;//补充说明
	private  String mistakeBarcode;//错漏发条形码
	private String mistakeProductCount;//错发商品数量
	private String pauseReason;
	private String estimatedTimeOfCompletion;
	private BigDecimal standardManhours;
	private String processStartPoint;
	private BigDecimal actualManhours;
	private String terminalTime;
	private String watched;
	private String cal_date;
	private String level_starttime;
	private String fileName_common;
	private String claimId;
	private String packageId;
	
	
    public String getPackageId(){
        return packageId;
    }
    
    public void setPackageId(String packageId){
        this.packageId = packageId;
    }
    public String getBySelf() {
		return bySelf;
	}
	public void setBySelf(String bySelf) {
		this.bySelf = bySelf;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
	public String getResourceWoNo() {
		return resourceWoNo;
	}
	public void setResourceWoNo(String resourceWoNo) {
		this.resourceWoNo = resourceWoNo;
	}
	public String getWoSource() {
		return woSource;
	}
	public void setWoSource(String woSource) {
		this.woSource = woSource;
	}
	public String getWoAllocStatus() {
		return woAllocStatus;
	}
	public void setWoAllocStatus(String woAllocStatus) {
		this.woAllocStatus = woAllocStatus;
	}
	public String getWoAllocStatusDisplay() {
		return woAllocStatusDisplay;
	}
	public void setWoAllocStatusDisplay(String woAllocStatusDisplay) {
		this.woAllocStatusDisplay = woAllocStatusDisplay;
	}
	public String getWoProcessStatus() {
		return woProcessStatus;
	}
	public void setWoProcessStatus(String woProcessStatus) {
		this.woProcessStatus = woProcessStatus;
	}
	public String getWoProcessStatusDisplay() {
		return woProcessStatusDisplay;
	}
	public void setWoProcessStatusDisplay(String woProcessStatusDisplay) {
		this.woProcessStatusDisplay = woProcessStatusDisplay;
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
	public String getWoLevel() {
		return woLevel;
	}
	public void setWoLevel(String woLevel) {
		this.woLevel = woLevel;
	}
	public String getWoLevelDisplay() {
		return woLevelDisplay;
	}
	public void setWoLevelDisplay(String woLevelDisplay) {
		this.woLevelDisplay = woLevelDisplay;
	}
	public String getLevelAlterReason() {
		return levelAlterReason;
	}
	public void setLevelAlterReason(String levelAlterReason) {
		this.levelAlterReason = levelAlterReason;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getCarriers() {
		return carriers;
	}
	public void setCarriers(String carriers) {
		this.carriers = carriers;
	}
	public String getCarriersDisplay() {
		return carriersDisplay;
	}
	public void setCarriersDisplay(String carriersDisplay) {
		this.carriersDisplay = carriersDisplay;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getLogisticsStatus() {
		return logisticsStatus;
	}
	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}
	public String getWarningType() {
		return warningType;
	}
	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}
	public String getWarningLevel() {
		return warningLevel;
	}
	public void setWarningLevel(String warningLevel) {
		this.warningLevel = warningLevel;
	}
	public String getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(String warehouses) {
		this.warehouses = warehouses;
	}
	public String getWarehousesDisplay() {
		return warehousesDisplay;
	}
	public void setWarehousesDisplay(String warehousesDisplay) {
		this.warehousesDisplay = warehousesDisplay;
	}
	public String getTransportTime() {
		return transportTime;
	}
	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}
	public String getStores() {
		return stores;
	}
	public void setStores(String stores) {
		this.stores = stores;
	}
	public String getStoresDisplay() {
		return storesDisplay;
	}
	public void setStoresDisplay(String storesDisplay) {
		this.storesDisplay = storesDisplay;
	}
	public String getPlatformNumber() {
		return platformNumber;
	}
	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}
	public String getRelatedNumber() {
		return relatedNumber;
	}
	public void setRelatedNumber(String relatedNumber) {
		this.relatedNumber = relatedNumber;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQueryPerson() {
		return queryPerson;
	}
	public void setQueryPerson(String queryPerson) {
		this.queryPerson = queryPerson;
	}
	public String getAllocatedBy() {
		return allocatedBy;
	}
	public void setAllocatedBy(String allocatedBy) {
		this.allocatedBy = allocatedBy;
	}
	public String getAllocatedByDisplay() {
		return allocatedByDisplay;
	}
	public void setAllocatedByDisplay(String allocatedByDisplay) {
		this.allocatedByDisplay = allocatedByDisplay;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getProcessorDisplay() {
		return processorDisplay;
	}
	public void setProcessorDisplay(String processorDisplay) {
		this.processorDisplay = processorDisplay;
	}
    public String getProcessContent() {
		return processContent;
	}
	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}

	public String getSupplementExplain() {
		return supplementExplain;
	}

	public void setSupplementExplain(String supplementExplain) {
		this.supplementExplain = supplementExplain;
	}

	public String getMistakeBarcode() {
		return mistakeBarcode;
	}

	public void setMistakeBarcode(String mistakeBarcode) {
		this.mistakeBarcode = mistakeBarcode;
	}

	public String getMistakeProductCount() {
		return mistakeProductCount;
	}

	public void setMistakeProductCount(String mistakeProductCount) {
		this.mistakeProductCount = mistakeProductCount;
	}

	public String getPauseReason() {
		return pauseReason;
	}
	public void setPauseReason(String pauseReason) {
		this.pauseReason = pauseReason;
	}
	public String getEstimatedTimeOfCompletion() {
		return estimatedTimeOfCompletion;
	}
	public void setEstimatedTimeOfCompletion(String estimatedTimeOfCompletion) {
		this.estimatedTimeOfCompletion = estimatedTimeOfCompletion;
	}
	public BigDecimal getStandardManhours() {
		return standardManhours;
	}
	public void setStandardManhours(BigDecimal standardManhours) {
		this.standardManhours = standardManhours;
	}
	public String getProcessStartPoint() {
		return processStartPoint;
	}
	public void setProcessStartPoint(String processStartPoint) {
		this.processStartPoint = processStartPoint;
	}
	public BigDecimal getActualManhours() {
		return actualManhours;
	}
	public void setActualManhours(BigDecimal actualManhours) {
		this.actualManhours = actualManhours;
	}
	public String getTerminalTime() {
		return terminalTime;
	}
	public void setTerminalTime(String terminalTime) {
		this.terminalTime = terminalTime;
	}
	public String getWatched() {
		return watched;
	}
	public void setWatched(String watched) {
		this.watched = watched;
	}
	public String getCal_date() {
		return cal_date;
	}
	public void setCal_date(String cal_date) {
		this.cal_date = cal_date;
	}
	public String getLevel_starttime() {
		return level_starttime;
	}
	public void setLevel_starttime(String level_starttime) {
		this.level_starttime = level_starttime;
	}
	public String getFileName_common() {
		return fileName_common;
	}
	public void setFileName_common(String fileName_common) {
		this.fileName_common = fileName_common;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	
}