package com.lmis.pos.accReport.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日
 */
public class PosPlanWareAnalyAreaScale extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "开始日期")
	private String beginDateTime;
    
    @ApiModelProperty(value = "结束日期")
	private String endDateTime;
    
    @ApiModelProperty(value = "日期")
	private String crd;
    
    @ApiModelProperty(value = "计划入库数")
	private String qty;
    
    @ApiModelProperty(value = "bu")
	private String bu;
    
    @ApiModelProperty(value = "仓库名称")
	private String whsName;
    
    @ApiModelProperty(value = "仓库编码")
	private String whsCode;

	public String getWhsCode() {
		return whsCode;
	}

	public void setWhsCode(String whsCode) {
		this.whsCode = whsCode;
	}

	public String getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getCrd() {
		return crd;
	}

	public void setCrd(String crd) {
		this.crd = crd;
	}


	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getWhsName() {
		return whsName;
	}

	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}

     
    
    
}
