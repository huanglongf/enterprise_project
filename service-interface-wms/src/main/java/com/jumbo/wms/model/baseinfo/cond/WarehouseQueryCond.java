/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.baseinfo.cond;

import com.jumbo.wms.model.BaseResponse;

/**
 * 仓库信息查询实体
 * 
 * @author ShengGe
 * 
 */
public class WarehouseQueryCond extends BaseResponse{

    
    /**
     * 
     */
    private static final long serialVersionUID = -8055908643103772379L;
    /** 渠道编码 */
    private String channelCode;
    /** 仓储组织编码 */
    private String whCode;
    /** 负责人姓名 */
    private String pic;
    /** 负责人联系方式 */
    private String picContact;
    /** 仓库电话 */
    private String telphone;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 区 */
    private String district;
    /** 仓库地址 */
    private String address;
    /** 邮编 */
    private String zipcode;
    /** 其他联系人及方式1 */
    private String otherContact1;
    /** 其他联系人及方式2 */
    private String otherContact2;
    /** 其他联系人及方式3 */
    private String otherContact3;
    /** 扩展字段1 */
    private String ext_str1;
    /** 扩展字段2 */
    private String ext_str2;
    /** 扩展字段3 */
    private String ext_str3;
    
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getWhCode() {
        return whCode;
    }
    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getPicContact() {
        return picContact;
    }
    public void setPicContact(String picContact) {
        this.picContact = picContact;
    }
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getOtherContact1() {
        return otherContact1;
    }
    public void setOtherContact1(String otherContact1) {
        this.otherContact1 = otherContact1;
    }
    public String getOtherContact2() {
        return otherContact2;
    }
    public void setOtherContact2(String otherContact2) {
        this.otherContact2 = otherContact2;
    }
    public String getOtherContact3() {
        return otherContact3;
    }
    public void setOtherContact3(String otherContact3) {
        this.otherContact3 = otherContact3;
    }
    public String getExt_str1() {
        return ext_str1;
    }
    public void setExt_str1(String ext_str1) {
        this.ext_str1 = ext_str1;
    }
    public String getExt_str2() {
        return ext_str2;
    }
    public void setExt_str2(String ext_str2) {
        this.ext_str2 = ext_str2;
    }
    public String getExt_str3() {
        return ext_str3;
    }
    public void setExt_str3(String ext_str3) {
        this.ext_str3 = ext_str3;
    }
    
}
