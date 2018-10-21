package com.lmis.sys.errLog.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysErrLog
 * @Description: TODO()
 * @author codeGenerator
 * @date 2018-05-09 14:31:13
 * 
 */
public class SysErrLog extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "调用方法")
	private String byFunction;
	public String getByFunction() {
		return byFunction;
	}
	public void setByFunction(String byFunction) {
		this.byFunction = byFunction;
	}
	
    @ApiModelProperty(value = "异常数据")
	private String errData;
	public String getErrData() {
		return errData;
	}
	public void setErrData(String errData) {
		this.errData = errData;
	}
	
    @ApiModelProperty(value = "异常信息")
	private String errLog;
	public String getErrLog() {
		return errLog;
	}
	public void setErrLog(String errLog) {
		this.errLog = errLog;
	}
	
}
