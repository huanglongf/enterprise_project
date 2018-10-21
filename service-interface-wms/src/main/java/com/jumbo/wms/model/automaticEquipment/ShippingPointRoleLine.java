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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransTimeType;

/**
 * 集货规则明细
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_shipping_role_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ShippingPointRoleLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8567885886530504453L;
    /**
     * PK
     */
    private Long id;
    /**
     * 优先级
     */
    private Long sort;

    /**
     * 店铺
     */
    private String owner;
    /**
     * 物流商
     */
    private String lpcode;

    /**
     * 时效类型
     */
    private TransTimeType timeType;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;

    /**
     * 是否货到付款
     */
    private Boolean isCod;
    /**
     * 作业单
     */
    private String staCode;
    /**
     * 作业类型
     */
    private StockTransApplicationType type;

    /**
     * 配货规则
     */
    private ShippingPoint point;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 地址
     */
    private String address;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SHIPPING_ROLE_LINE", sequenceName = "s_t_wh_shipping_role_line", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHIPPING_ROLE_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SORT")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "lpcode")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "time_type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
    public TransTimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TransTimeType timeType) {
        this.timeType = timeType;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "is_cod")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "sta_code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "sta_type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationType")})
    public StockTransApplicationType getType() {
        return type;
    }

    public void setType(StockTransApplicationType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    public ShippingPoint getPoint() {
        return point;
    }

    public void setPoint(ShippingPoint point) {
        this.point = point;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
