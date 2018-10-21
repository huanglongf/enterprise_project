package com.lmis.pos.crdOut.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosCrdOut
 * @Description: TODO(不拆分CRD设置)
 * @author codeGenerator
 * @date 2018-06-01 17:09:27
 * 
 */
public class PosCrdOut extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "不拆分CRD日期")
	private String crdOut;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    public String getCrdOut() {
		return crdOut;
	}
	public void setCrdOut(String crdOut) {
		this.crdOut = crdOut;
	}
    
	@ApiModelProperty(value = "主键")
	private String id;
	

	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	
	@ApiModelProperty(value = "创建对象")
	private String createBy;
	
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	
	@ApiModelProperty(value = "更新对象")
	private String updateBy;
	
	@ApiModelProperty(value = "逻辑删除标志")
	private Boolean isDeleted;
	
	@ApiModelProperty(value = "启停标志")
	private Boolean isDisabled;
	
	@ApiModelProperty(value = "版本号")
	private Integer version;
	
	@ApiModelProperty(value = "权限架构")
	private String pwrOrg;

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
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getPwrOrg() {
		return pwrOrg;
	}
	public void setPwrOrg(String pwrOrg) {
		this.pwrOrg = pwrOrg;
	}
	

	
	
}
