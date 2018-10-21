package com.lmis.framework.baseModel;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PersistentObject
 * @Description: TODO(持久化基类)
 * @author Ian.Huang
 * @date 2017年10月8日 下午4:52:37 
 * 
 */
public class PersistentObject implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3992612456987127995L;

	/** 
	 * @Fields id : TODO(主键ID)   
	 */
	@ApiModelProperty(value = "系统主键，修改/删除时必输")
	private String id;
	/** 
	 * @Fields createTime : TODO(创建时间) 
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间，系统后台生成")
	private Date createTime;
	/** 
	 * @Fields createBy : TODO(创建对象ID) 
	 */
	@ApiModelProperty(value = "创建人，系统后台生成")
	private String createBy;
	/** 
	 * @Fields updateTime : TODO(更新时间) 
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "最后修改时间，系统后台生成")
	private Date updateTime;
	/** 
	 * @Fields updateBy : TODO(更新对象ID) 
	 */
	@ApiModelProperty(value = "最后修改人，系统后台生成")
	private String updateBy;
	/** 
	 * @Fields isDeleted : TODO(逻辑删除标志 1-已删除 0-未删除) 
	 */
	@ApiModelProperty(value = "是否删除")
	private Boolean isDeleted;
	/** 
	 * @Fields isDisabled : TODO(启停标志 1-已禁用 0-未禁用)
	 */
	@ApiModelProperty(value = "启停标志")
	private Boolean isDisabled;
	/** 
	 * @Fields isDisabled : TODO(版本号)
	 */
	@ApiModelProperty(value = "版本号")
	private Integer version;
	/** 
	 * @Fields isDisabled : TODO(所属机构)
	 */
	@ApiModelProperty(value = "所属机构")
	private String pwrOrg;
	/**
	 * 当前用户信息
	 */
	private String orgUserId;
	/**
	 * 是否需要数据控制
	 */
	private boolean isAuthorityCro =false;

	public PersistentObject() {}

	public PersistentObject(String id, Date createTime, String createBy, Date updateTime, String updateBy,
			Boolean isDeleted, Boolean isDisabled, Integer version, String pwrOrg) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.createBy = createBy;
		this.updateTime = updateTime;
		this.updateBy = updateBy;
		this.isDeleted = isDeleted;
		this.isDisabled = isDisabled;
		this.version = version;
		this.pwrOrg = pwrOrg;
	}
	
	
	
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
	
	public String getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(String orgUserId) {
		this.orgUserId = orgUserId;
	}
	
    public boolean isAuthorityCro(){
        return isAuthorityCro;
    }

    
    public void setAuthorityCro(boolean isAuthorityCro){
        this.isAuthorityCro = isAuthorityCro;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (非 Javadoc) 
	 * <p>Title: equals</p> 
	 * <p>Description: 覆盖原equals方法，只要对象类型相同并且主键相同，那么返回true，表示两个对象相等</p> 
	 * @param o
	 * @return 
	 * @see java.lang.Object#equals(java.lang.Object) 
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof PersistentObject)) {
			return false;
		}
		PersistentObject other = (PersistentObject) o;
		return id.equals(other.getId());
	}
	
	/* (非 Javadoc) 
	 * <p>Title: hashCode</p> 
	 * <p>Description: 覆盖原hashCode方法，用主键的hashcode代替原来对象的hashcode，可以简化持久化对象的比较</p> 
	 * @return 
	 * @see java.lang.Object#hashCode() 
	 */
	public int hashCode() {
		if (id == null) {
			return Integer.valueOf(0);
		}
		return id.hashCode();
	}
	
	/* (非 Javadoc) 
	 * <p>Title: toString</p> 
	 * <p>Description: 覆盖原toString方法，打印更详细信息</p> 
	 * @return 
	 * @see java.lang.Object#toString() 
	 */
	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]";
	}
	
}
