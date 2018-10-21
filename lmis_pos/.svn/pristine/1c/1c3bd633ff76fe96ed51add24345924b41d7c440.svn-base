package com.lmis.pos.addrLib.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosAddrLib
 * @Description: TODO(地址库表（基础数据）)
 * @author codeGenerator
 * @date 2018-05-29 10:46:34
 * 
 */
public class PosAddrLib extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "代码")
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
    @ApiModelProperty(value = "名称")
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    @ApiModelProperty(value = "级别")
	private Integer level;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
    @ApiModelProperty(value = "父节点ID")
	private String superior;
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	
    @ApiModelProperty(value = "存在子节点 存在-0 不存在-1")
	private Boolean haschild;
	public Boolean getHaschild() {
		return haschild;
	}
	public void setHaschild(Boolean haschild) {
		this.haschild = haschild;
	}
	
}
