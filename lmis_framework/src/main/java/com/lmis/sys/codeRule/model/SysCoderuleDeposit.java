package com.lmis.sys.codeRule.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysCoderuleDeposit
 * @Description: TODO()
 * @author codeGenerator
 * @date 2018-05-17 13:16:21
 * 
 */
public class SysCoderuleDeposit extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    @ApiModelProperty(value = "结果值(如XX201801010001)")
	private String resultValue;
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	
    @ApiModelProperty(value = "编码信息code")
	private String configCode;
	
	
    
    public String getConfigCode(){
        return configCode;
    }
    
    public void setConfigCode(String configCode){
        this.configCode = configCode;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "自增长周期相关时间")
	private Date growthTime;
	public Date getGrowthTime() {
		return growthTime;
	}
	public void setGrowthTime(Date growthTime) {
		this.growthTime = growthTime;
	}
	
}
