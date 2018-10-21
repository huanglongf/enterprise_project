package com.lmis.pos.common.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosDataFileTemplete
 * @Description: TODO(下载上传excel模板统一记录表)
 * @author codeGenerator
 * @date 2018-05-29 15:38:54
 * 
 */
public class PosDataFileTemplete extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "模版名称")
	private String templeteName;
	public String getTempleteName() {
		return templeteName;
	}
	public void setTempleteName(String templeteName) {
		this.templeteName = templeteName;
	}
	
    @ApiModelProperty(value = "业务类型(原则唯一，外部需根据此字段查询模板)")
	private String businessType;
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
    @ApiModelProperty(value = "业务组，多个模板可同组(某个功能一个组，组内可有不同上传模板)")
	private String businessGroup;
	public String getBusinessGroup() {
		return businessGroup;
	}
	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}
	
    @ApiModelProperty(value = "模板类型0.导入1.导出")
	private String templeteType;
	public String getTempleteType() {
		return templeteType;
	}
	public void setTempleteType(String templeteType) {
		this.templeteType = templeteType;
	}
	
    @ApiModelProperty(value = "模板表头映射(标准json对象{colunm(表字段名):title(表头)})")
	private String templeteTitleMap;
	public String getTempleteTitleMap() {
		return templeteTitleMap;
	}
	public void setTempleteTitleMap(String templeteTitleMap) {
		this.templeteTitleMap = templeteTitleMap;
	}
	
    @ApiModelProperty(value = "模板提供方式0.自动生成，1.静态资源模板可提供预定义样式")
	private String templeteMode;
	public String getTempleteMode() {
		return templeteMode;
	}
	public void setTempleteMode(String templeteMode) {
		this.templeteMode = templeteMode;
	}

    @ApiModelProperty(value = "模板文件id(pos_data_file_log表id),templete_mode为1时有效")
	private String fileId;
    public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@ApiModelProperty(value = "静态资源模板提供路径templete_mode为1时有效")
	private String templeteModeUrl;
	public String getTempleteModeUrl() {
		return templeteModeUrl;
	}
	public void setTempleteModeUrl(String templeteModeUrl) {
		this.templeteModeUrl = templeteModeUrl;
	}
	
    @ApiModelProperty(value = "表头长度(列长度)")
	private Integer titleLength;
	public Integer getTitleLength() {
		return titleLength;
	}
	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}
	
    @ApiModelProperty(value = "模板实现Maper的spring实例名,templete_type=1时则调用此实例的retrieveAsMap方法导出数据(注此实例必须实现BaseExcelMapper接口)")
	private String templeteImpl;
	public String getTempleteImpl() {
		return templeteImpl;
	}
	public void setTempleteImpl(String templeteImpl) {
		this.templeteImpl = templeteImpl;
	}
	
    @ApiModelProperty(value = "模板实现Maper的model类名(全限定包+类名),templete_type=1时作为Maper的条件参数")
	private String templeteModel;
	public String getTempleteModel() {
		return templeteModel;
	}
	public void setTempleteModel(String templeteModel) {
		this.templeteModel = templeteModel;
	}
	
    @ApiModelProperty(value = "模板对应上传url例:/control/importTaskController/expressCoverImport.do")
	private String uploadUrl;
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	
    @ApiModelProperty(value = "历史版本标识")
	private String historyVersion;
	public String getHistoryVersion() {
		return historyVersion;
	}
	public void setHistoryVersion(String historyVersion) {
		this.historyVersion = historyVersion;
	}
	
    @ApiModelProperty(value = "备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    @ApiModelProperty(value = "删除标记0.正常，1已删除")
	private String delFlag;
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}
