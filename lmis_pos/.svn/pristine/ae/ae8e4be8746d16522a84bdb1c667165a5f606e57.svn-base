package com.lmis.pos.whsOutPlan.model;

import java.math.BigDecimal;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsOutPlan
 * @Description: TODO(仓库出库计划)
 * @author codeGenerator
 * @date 2018-05-29 15:13:58
 * 
 */
public class PosWhsOutPlan extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    private String startDate;
    private String endDate;
    public String getStartDate(){
        return startDate;
    }
    
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    
    public String getEndDate(){
        return endDate;
    }
    
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    
    @ApiModelProperty(value = "仓库编码")
	private String whsCode;
	public String getWhsCode() {
		return whsCode;
	}
	public void setWhsCode(String whsCode) {
		this.whsCode = whsCode;
	}
	
    @ApiModelProperty(value = "出库日期")
	private String scheduleDate;
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	
    @ApiModelProperty(value = "计划出库数")
	private Integer PlannedOut;
	public Integer getPlannedOut() {
		return PlannedOut;
	}
	public void setPlannedOut(Integer PlannedOut) {
		this.PlannedOut = PlannedOut;
	}
	
    @ApiModelProperty(value = "计划出库数（鞋类）")
	private Integer PlannedOutShoe;
	public Integer getPlannedOutShoe() {
		return PlannedOutShoe;
	}
	public void setPlannedOutShoe(Integer PlannedOutShoe) {
		this.PlannedOutShoe = PlannedOutShoe;
	}
	
    @ApiModelProperty(value = "计划出库数(服饰类）")
	private Integer PlannedOutAddress;
	public Integer getPlannedOutAddress() {
		return PlannedOutAddress;
	}
	public void setPlannedOutAddress(Integer PlannedOutAddress) {
		this.PlannedOutAddress = PlannedOutAddress;
	}
	
    @ApiModelProperty(value = "计划出库数(配饰类）")
	private Integer PlannedOutAccessory;
	public Integer getPlannedOutAccessory() {
		return PlannedOutAccessory;
	}
	public void setPlannedOutAccessory(Integer PlannedOutAccessory) {
		this.PlannedOutAccessory = PlannedOutAccessory;
	}
	
    @ApiModelProperty(value = "相关任务号 20180528{两位流水}")
	private String relatedTaskNo;
	public String getRelatedTaskNo() {
		return relatedTaskNo;
	}
	public void setRelatedTaskNo(String relatedTaskNo) {
		this.relatedTaskNo = relatedTaskNo;
	}
	
}
