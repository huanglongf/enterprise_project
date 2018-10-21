package com.lmis.pos.common.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ServiceDataFileLog
 * @Description: TODO(文件管存记录表)
 * @author codeGenerator
 * @date 2018-04-11 15:58:57
 * 
 */
public class ServiceDataFileLog extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "文件路径")
	private String filePath;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
    @ApiModelProperty(value = "文件名称")
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
    @ApiModelProperty(value = "初始大小")
	private Long dataCount;
    public Long getDataCount() {
		return dataCount;
	}
	public void setDataCount(Long dataCount) {
		this.dataCount = dataCount;
	}

	@ApiModelProperty(value = "已处理大小")
	private Long opsCount;
	public Long getOpsCount() {
		return opsCount;
	}
	public void setOpsCount(Long opsCount) {
		this.opsCount = opsCount;
	}
	
    @ApiModelProperty(value = "处理状态0:待处理1:处理中 2:处理成功 3:处理失败")
	private Integer opsStatus;
	public Integer getOpsStatus() {
		return opsStatus;
	}
	public void setOpsStatus(Integer opsStatus) {
		this.opsStatus = opsStatus;
	}
	
    @ApiModelProperty(value = "操作异常信息")
	private String opsErrorInfo ;
	public String getOpsErrorInfo() {
		return opsErrorInfo;
	}
	public void setOpsErrorInfo(String opsErrorInfo) {
		this.opsErrorInfo = opsErrorInfo;
	}
	
}
