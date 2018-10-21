package com.lmis.pos.whsSurplusSc.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsSurplusSc
 * @Description: TODO(仓库剩余入库能力分析)
 * @author codeGenerator
 * @date 2018-06-01 14:19:50
 * 
 */
public class PosWhsSurplusSc extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }

    @ApiModelProperty(value = "是否需要大于scheduleDate的其他排班记录")
    private Boolean needScheduleDateRight;
    public Boolean getNeedScheduleDateRight() {
		return needScheduleDateRight;
	}
	public void setNeedScheduleDateRight(Boolean needScheduleDateRight) {
		this.needScheduleDateRight = needScheduleDateRight;
	}

	@ApiModelProperty(value = "计划日期")
	private String scheduleDate;
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	
    @ApiModelProperty(value = "仓库编码")
	private String whsCode;
	public String getWhsCode() {
		return whsCode;
	}
	public void setWhsCode(String whsCode) {
		this.whsCode = whsCode;
	}
	
    @ApiModelProperty(value = "SKU类型（鞋服配）")
	private String skuType;
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}
	
    @ApiModelProperty(value = "最大库容")
	private Integer scMax;
	public Integer getScMax() {
		return scMax;
	}
	public void setScMax(Integer scMax) {
		this.scMax = scMax;
	}
	
    @ApiModelProperty(value = "初始库容")
	private Integer scInit;
	public Integer getScInit() {
		return scInit;
	}
	public void setScInit(Integer scInit) {
		this.scInit = scInit;
	}
	
    @ApiModelProperty(value = "计划出库数")
	private Integer plannedOut;
	public Integer getPlannedOut() {
		return plannedOut;
	}
	public void setPlannedOut(Integer plannedOut) {
		this.plannedOut = plannedOut;
	}
	
    @ApiModelProperty(value = "po计划入库数")
	private Integer plannedInPo;
	public Integer getPlannedInPo() {
		return plannedInPo;
	}
	public void setPlannedInPo(Integer plannedInPo) {
		this.plannedInPo = plannedInPo;
	}
	
    @ApiModelProperty(value = "可用库容")
	private Integer surplus;
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	
    @ApiModelProperty(value = "可用库容累计")
	private Integer surplusPlus;
	public Integer getSurplusPlus() {
		return surplusPlus;
	}
	public void setSurplusPlus(Integer surplusPlus) {
		this.surplusPlus = surplusPlus;
	}
	
    @ApiModelProperty(value = "最大入库作业数")
	private Integer inJobsMax;
	public Integer getInJobsMax() {
		return inJobsMax;
	}
	public void setInJobsMax(Integer inJobsMax) {
		this.inJobsMax = inJobsMax;
	}
	
    @ApiModelProperty(value = "可作业入库数")
	private Integer inJobsEnable;
	public Integer getInJobsEnable() {
		return inJobsEnable;
	}
	public void setInJobsEnable(Integer inJobsEnable) {
		this.inJobsEnable = inJobsEnable;
	}
	
    @ApiModelProperty(value = "PO可计划入库数")
	private Integer inEnable;
	public Integer getInEnable() {
		return inEnable;
	}
	public void setInEnable(Integer inEnable) {
		this.inEnable = inEnable;
	}
	
    @ApiModelProperty(value = "PO可计划入库数累计")
	private Integer inEnablePlus;
	public Integer getInEnablePlus() {
		return inEnablePlus;
	}
	public void setInEnablePlus(Integer inEnablePlus) {
		this.inEnablePlus = inEnablePlus;
	}
	
    @ApiModelProperty(value = "预估期末库存")
	private Integer finalInventory;
	public Integer getFinalInventory() {
		return finalInventory;
	}
	public void setFinalInventory(Integer finalInventory) {
		this.finalInventory = finalInventory;
	}
	
    @ApiModelProperty(value = "日期id")
	private String dateId;
	public String getDateId() {
		return dateId;
	}
	public void setDateId(String dateId) {
		this.dateId = dateId;
	}
	
}
