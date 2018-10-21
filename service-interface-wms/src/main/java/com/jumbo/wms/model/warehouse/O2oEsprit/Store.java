/**
 * Copyright (c) 2013 Baozun All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Baozun.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Baozun.
 *
 * BAOZUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. BAOZUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.util.Date;

import lark.common.model.BaseModel;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author lzb
 * 
 */

public class Store extends BaseModel {

    private static final long serialVersionUID = -3369765790920812304L;
    /** 门店唯一标志 */
    private String code;
    /** 门店名称 */
    private String name;
    /** 品牌标志，用于区分门店所属 */
    private String brandCode;
    /** 1.可用;2.已删除 */
    private Integer lifecycle;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
    /** 品牌定制编码 */
    private String extCode;
    /** 品牌定制属性:json格式的字符串 */
    private String extProperties;
    /** 操作类型 */
    private int type;

    public Store() {}

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getExtProperties() {
        return extProperties;
    }

    public void setExtProperties(String extProperties) {
        this.extProperties = extProperties;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCode(java.lang.String value) {
        this.code = value;
    }

    public java.lang.String getCode() {
        return this.code;
    }

    public void setName(java.lang.String value) {
        this.name = value;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setBrandCode(java.lang.String value) {
        this.brandCode = value;
    }

    public java.lang.String getBrandCode() {
        return this.brandCode;
    }

    public void setLifecycle(Integer value) {
        this.lifecycle = value;
    }

    public Integer getLifecycle() {
        return this.lifecycle;
    }

    public void setCreateTime(java.util.Date value) {
        this.createTime = value;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Id", getId()).append("Code", getCode()).append("Name", getName()).append("BrandCode", getBrandCode()).append("Lifecycle", getLifecycle()).append("CreateTime", getCreateTime()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).append(getCode()).append(getName()).append(getBrandCode()).append(getLifecycle()).append(getCreateTime()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Store == false) {return false;}
        if (this == obj) {return true;}
        Store other = (Store) obj;
        return new EqualsBuilder().append(getId(), other.getId())

        .append(getCode(), other.getCode())

        .append(getName(), other.getName())

        .append(getBrandCode(), other.getBrandCode())

        .append(getLifecycle(), other.getLifecycle())

        .append(getCreateTime(), other.getCreateTime())

        .isEquals();
    }
}

