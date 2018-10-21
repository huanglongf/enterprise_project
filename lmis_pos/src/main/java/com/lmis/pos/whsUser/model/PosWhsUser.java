package com.lmis.pos.whsUser.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsUser
 * @Description: TODO(仓库用户)
 * @author codeGenerator
 * @date 2018-05-29 16:31:04
 * 
 */
public class PosWhsUser extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "仓库编码")
	private String whsCode;
	public String getWhsCode() {
		return whsCode;
	}
	public void setWhsCode(String whsCode) {
		this.whsCode = whsCode;
	}
	
    @ApiModelProperty(value = "用户编码")
	private String userCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
}
