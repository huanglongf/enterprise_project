package com.lmis.jbasis.transportVendor.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: JbasisTransportVendor
 * @Description: TODO(运输供应商(基础数据))
 * @author codeGenerator
 * @date 2018-04-19 11:14:05
 * 
 */
public class JbasisTransportVendor extends PersistentObject {

    /** 
     * @Fields serialVersionUID : TODO(序列号) 
     */
    private static final long serialVersionUID = 1L;
    
    public static long getSerialversionuid() {
        return serialVersionUID;    
    }
    
    @ApiModelProperty(value = "运输编码")
    private String transportCode;
    public String getTransportCode() {
        return transportCode;
    }
    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }
    
    @ApiModelProperty(value = "运输名称")
    private String transportName;
    public String getTransportName() {
        return transportName;
    }
    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }
    
    @ApiModelProperty(value = "联系人")
    private String contact;
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    @ApiModelProperty(value = "联系电话")
    private String phone;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @ApiModelProperty(value = "运输类型(wlslx_1:物流；wlslx_2：快递)")
    private String transportType;
    public String getTransportType() {
        return transportType;
    }
    
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
    
}
