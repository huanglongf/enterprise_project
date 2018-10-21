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

package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 集获口
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_shipping_point")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ShippingPoint extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8567885886530504453L;
    /**
     * PK
     */
    private Long id;

    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * wcs编码
     */
    private String wcsCode;
    /**
     * 仓库ID
     */
    private Long ouId;
    /**
     * 创建人ID
     */
    private Long createId;
    /**
     * 状态： 0禁用 1使用
     */
    private Long status;
    /**
     * 最后修改人
     */
    private Long lastModifyId;
    /**
     * 创建时间
     */
    private Date createTime;

    // 添加实现集货口负载均衡的相关字段
    /**
     * 集货口类型( 0：普通集货口; 1：负载均衡口(主导); 2：负载均衡口(从属))
     */
    private String pointType;

    /**
     * 实现负载均衡的关联字段：同一组的负载均衡口，该字段值为主导的负载均衡口的id
     */
    private String refShippingPoint;

    /**
     * 每次循环 每个同级别的负载口最多分配的作业单数量
     */
    private Long maxAssumeNumber;

    /**
     * mongodb中保存的每次循环 单个集货口已经分配的数量
     */
    private Long keepNumber;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SHIPPING_POINT", sequenceName = "s_t_wh_shipping_point", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHIPPING_POINT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "wcs_code")
    public String getWcsCode() {
        return wcsCode;
    }

    public void setWcsCode(String wcsCode) {
        this.wcsCode = wcsCode;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "create_id")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Column(name = "status")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "last_modify_id")
    public Long getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(Long lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "point_type")
    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    @Column(name = "ref_shippingPoint")
    public String getRefShippingPoint() {
        return refShippingPoint;
    }

    public void setRefShippingPoint(String refShippingPoint) {
        this.refShippingPoint = refShippingPoint;
    }

    @Column(name = "max_assume_number")
    public Long getMaxAssumeNumber() {
        return maxAssumeNumber;
    }

    public void setMaxAssumeNumber(Long maxAssumeNumber) {
        this.maxAssumeNumber = maxAssumeNumber;
    }

    @Transient
    public Long getKeepNumber() {
        return keepNumber;
    }

    public void setKeepNumber(Long keepNumber) {
        this.keepNumber = keepNumber;
    }

    @Override
    public String toString() {
        return "ShippingPoint [id=" + id + ", code=" + code + ", name=" + name + ", wcsCode=" + wcsCode + ", ouId=" + ouId + ", createId=" + createId + ", status=" + status + ", lastModifyId=" + lastModifyId + ", createTime=" + createTime + ", type="
                + pointType + ", refShippingPoint=" + refShippingPoint + ", maxAssumeNumber=" + maxAssumeNumber + ", keepNumber=" + keepNumber + "]";
    }
}
