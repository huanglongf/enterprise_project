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

package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 物流商提供条码列表
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_PROVIDE_NO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhTransProvideNo extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1067247275540049046L;

    /**
     * PK
     */
    private Long id;
    /**
     * 运单号
     */
    private String transno;
    /**
     * 作业单ID
     */
    private Long staid;
    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 版本号
     */
    private int version;
    /**
     * 用于排序
     */
    private Long sort;
    /**
     * 店铺名称
     */
    private String owner;

    /**
     * 是否支持COD
     */
    private Boolean isCod;
    /**
     * O2O装箱ID
     */
    private Long packageId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 过期时间
     */
    private Date expTime;
    /*
     * 空/1:出库单;5:发票单
     */
    private Integer type;
    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * 账号
     */
    private String emsAccount;
    /**
     * 月结账号
     */
    private String jcustid;
    /**
     * 是否子母单
     */
    private Boolean checkboxSf;



    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    @Column(name = "ems_account")
    public String getEmsAccount() {
        return emsAccount;
    }

    public void setEmsAccount(String emsAccount) {
        this.emsAccount = emsAccount;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRST", sequenceName = "S_T_WH_TRANSACTION_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRST")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANS_NO")
    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }

    @Column(name = "STA_ID")
    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "sort")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "PACKAGE_ID")
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "EXP_TIME")
    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "regionCode")
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Column(name = "jcustid")
    public String getJcustid() {
        return jcustid;
    }

    public void setJcustid(String jcustid) {
        this.jcustid = jcustid;
    }

    @Column(name = "checkbox_Sf")
    public Boolean getCheckboxSf() {
        return checkboxSf;
    }

    public void setCheckboxSf(Boolean checkboxSf) {
        this.checkboxSf = checkboxSf;
    }


}
