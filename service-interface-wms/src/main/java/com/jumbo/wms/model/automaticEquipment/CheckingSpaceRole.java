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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 复核工作台规则
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_checking_space_role")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CheckingSpaceRole extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3969947880439898209L;

    /**
     * PK
     */
    private Long id;
    /**
     * 仓库组织ID
     */
    private Long whOuId;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 是否QS订单
     */
    private Boolean isQs;
    /**
     * 特殊处理类型：1：特殊处理
     */
    private Long specialType;
    /**
     * OTO店铺
     */
    private String toLocation;
    /**
     * 优先级
     */
    private Integer lv;
    /**
     * 编码
     */
    private String code;
    /**
     * 复核工位编码
     */
    private String checkingAreaCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 类型： 1：单件复核工作台 2：多件复核播种墙
     */
    private Integer type;

    /**
     * 物流商
     */
    private String lpcode;

    /**
     * 优先发货城市
     */
    private String city;

    /**
     * 运单时限类型
     */
    private Integer transType;
    
    /**
     * 指定商品
     */
    private String skuCodes;
    
    /**
     * 是否预售订单
     */
    private String isPreSale;

    @Column(name = "TRANS_TYPE")
    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    @Column(name = "SKU_CODES")
    public String getSkuCodes() {
        return skuCodes;
    }



    public void setSkuCodes(String skuCodes) {
        this.skuCodes = skuCodes;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "seq_checking_space_role", sequenceName = "s_t_wh_checking_space_role", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_checking_space_role")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "WH_OU_ID")
    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "is_qs")
    public Boolean getIsQs() {
        return isQs;
    }

    public void setIsQs(Boolean isQs) {
        this.isQs = isQs;
    }

    @Column(name = "SPECIAL_TYPE")
    public Long getSpecialType() {
        return specialType;
    }


    public void setSpecialType(Long specialType) {
        this.specialType = specialType;
    }

    @Column(name = "TO_LOCATION")
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "lv")
    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "checking_area_code")
    public String getCheckingAreaCode() {
        return checkingAreaCode;
    }

    public void setCheckingAreaCode(String checkingAreaCode) {
        this.checkingAreaCode = checkingAreaCode;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "lpcode")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "IS_PRE_SALE")
    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }


}
