package com.lmis.jbasis.warehouse.model;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: ViewJbasisWarehouse
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-04-17 09:52:14
 * 
 */
public class ViewJbasisWarehouse{

    @ApiModelProperty(value = "主键")
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @ApiModelProperty(value = "创建人")
    private String createBy;

    public String getCreateBy(){
        return createBy;
    }

    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    public String getUpdateBy(){
        return updateBy;
    }

    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    @ApiModelProperty(value = "1 是删除 0未删除")
    private Boolean isDeleted;

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    @ApiModelProperty(value = "0是启用 1是禁用")
    private Boolean isDisabled;

    public Boolean getIsDisabled(){
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled){
        this.isDisabled = isDisabled;
    }

    @ApiModelProperty(value = "")
    private String isDisabledDisplay;

    public String getIsDisabledDisplay(){
        return isDisabledDisplay;
    }

    public void setIsDisabledDisplay(String isDisabledDisplay){
        this.isDisabledDisplay = isDisabledDisplay;
    }

    @ApiModelProperty(value = "版本")
    private Integer version;

    public Integer getVersion(){
        return version;
    }

    public void setVersion(Integer version){
        this.version = version;
    }

    @ApiModelProperty(value = "权限")
    private String pwrOrg;

    public String getPwrOrg(){
        return pwrOrg;
    }

    public void setPwrOrg(String pwrOrg){
        this.pwrOrg = pwrOrg;
    }

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    public String getWarehouseName(){
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName){
        this.warehouseName = warehouseName;
    }

    @ApiModelProperty(value = "仓库代码")
    private String warehouseCode;

    public String getWarehouseCode(){
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode){
        this.warehouseCode = warehouseCode;
    }

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    public String getProvinceName(){
        return provinceName;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    @ApiModelProperty(value = "市名称")
    private String cityName;

    public String getCityName(){
        return cityName;
    }

    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    @ApiModelProperty(value = "区名称")
    private String stateName;

    public String getStateName(){
        return stateName;
    }

    public void setStateName(String stateName){
        this.stateName = stateName;
    }

    @ApiModelProperty(value = "街道名称")
    private String streetName;

    public String getStreetName(){
        return streetName;
    }

    public void setStreetName(String streetName){
        this.streetName = streetName;
    }

    @ApiModelProperty(value = "详细地址")
    private String address;

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    @ApiModelProperty(value = "仓库类型")
    private String warehouseType;

    public String getWarehouseType(){
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType){
        this.warehouseType = warehouseType;
    }

    @ApiModelProperty(value = "仓库类型名称")
    private String warehouseTypeDisplay;

    public String getWarehouseTypeDisplay(){
        return warehouseTypeDisplay;
    }

    public void setWarehouseTypeDisplay(String warehouseTypeDisplay){
        this.warehouseTypeDisplay = warehouseTypeDisplay;
    }

    @ApiModelProperty(value = "联系人")
    private String contact;

    public String getContact(){
        return contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    @ApiModelProperty(value = "联系电话")
    private String phone;

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}
