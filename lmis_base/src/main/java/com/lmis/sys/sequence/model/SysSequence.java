package com.lmis.sys.sequence.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysSequence
 * @Description: TODO(自增长管理表)
 * @author codeGenerator
 * @date 2018-03-06 17:21:55
 * 
 */
public class SysSequence extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "自增长ID")
	private String seqId;
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
    @ApiModelProperty(value = "当前值")
	private Integer currentValue;
	public Integer getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
	
    @ApiModelProperty(value = "增长量")
	private Integer increment;
	public Integer getIncrement() {
		return increment;
	}
	public void setIncrement(Integer increment) {
		this.increment = increment;
	}
	
    @ApiModelProperty(value = "备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
