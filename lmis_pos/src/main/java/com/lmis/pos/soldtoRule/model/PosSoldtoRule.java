package com.lmis.pos.soldtoRule.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosSoldtoRule
 * @Description: TODO(销售平台拆单规则)
 * @author codeGenerator
 * @date 2018-06-06 15:08:30
 * 
 */
public class PosSoldtoRule extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "销售平台")
	private String soldto;
	public String getSoldto() {
		return soldto;
	}
	public void setSoldto(String soldto) {
		this.soldto = soldto;
	}
	
    @ApiModelProperty(value = "是否参与拆分")
	private Boolean isAllocated;
	public Boolean getIsAllocated() {
		return isAllocated;
	}
	public void setIsAllocated(Boolean isAllocated) {
		this.isAllocated = isAllocated;
	}
	
    @ApiModelProperty(value = "是否占用入库能力")
	private Boolean isScOccupy;
	public Boolean getIsScOccupy() {
		return isScOccupy;
	}
	public void setIsScOccupy(Boolean isScOccupy) {
		this.isScOccupy = isScOccupy;
	}
	
}
