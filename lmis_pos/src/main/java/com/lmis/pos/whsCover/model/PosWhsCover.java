package com.lmis.pos.whsCover.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsCover
 * @Description: TODO(仓库覆盖区域设置)
 * @author codeGenerator
 * @date 2018-05-31 09:46:54
 * 
 */
public class PosWhsCover extends PersistentObject {

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
	
    @ApiModelProperty(value = "省")
	private String province;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
    @ApiModelProperty(value = "省名")
	private String provinceName;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
    @ApiModelProperty(value = "市")
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
    @ApiModelProperty(value = "市名")
	private String cityName;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
