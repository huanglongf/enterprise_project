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

package com.jumbo.wms.model.warehouse.baoShui;


import java.math.BigDecimal;
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
 * 报关头表
 * @author lzb
 * CustomsDeclaration
 */

@Entity
@Table(name = "T_WH_CUSTOMS_DECLARATION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CustomsDeclaration extends BaseModel {
    private static final long serialVersionUID = 8685603958666096807L;
    
    private Long id;// 主键
    
    private String fromIdSource;//报关单唯一标识 系统生成FORM_ID_SOURCE
   
    private String wmsCode;//WMS单据号WMS_CODE
    
    private String slipCode;//相关单据号 SLIP_CODE
    
    private String platFromCode;//平台订单号 PLATFORM_CODE

    
    private String owner;//店铺 OWNER
    
    
    private String wmsType;//单据类型 WMS_TYPE
    
    private String wmsStatus;//单据状态 WMS_STATUS

    private String prestowageNo;//配载单号 PRESTOWAGE_NO
    
    private BigDecimal grossWt;//毛重 GROSS_WT

    private BigDecimal netWt;//净重 NET_WT

    private Integer packNo;//总件数 PACK_NO
    
    private String wrapType;//包装种类  WRAP_TYPE

    private String licensePlateNumber;//车牌号码  LICENSE_PLATE_NUMBER
    
    private Boolean isToModify;//申报数量是否变更 0:否 1:是   IS_TO_MODIFY

    private Integer status;//状态 1:未同步 2:同步成功 3:同步失败  

    
    private Long mainWhId;//仓库ID   MAIN_WH_ID

    private Date createTime;//创建时间
    
    private Long createId;//创建人ID  CREATE_ID
    
    private String createUser;//创建人 CREATE_USER

    private Integer version;// 版本   VERSION   

    private Integer type;//1：入库 2：出库
  
    private Integer isLoading;// 是否装车 1是0否
    
    private Integer errorCount;

    private String errorMsg;

    private Integer declarationStatus;


    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }



    public void setType(Integer type) {
        this.type = type;
    }



    @Column(name = "FORM_ID_SOURCE")
    public String getFromIdSource() {
        return fromIdSource;
    }



    public void setFromIdSource(String fromIdSource) {
        this.fromIdSource = fromIdSource;
    }


    @Column(name = "WMS_CODE")
    public String getWmsCode() {
        return wmsCode;
    }



    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }


    @Column(name = "SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }



    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }


    @Column(name = "PLATFORM_CODE")
    public String getPlatFromCode() {
        return platFromCode;
    }



    public void setPlatFromCode(String platFromCode) {
        this.platFromCode = platFromCode;
    }


    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }



    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Column(name = "WMS_TYPE")
    public String getWmsType() {
        return wmsType;
    }



    public void setWmsType(String wmsType) {
        this.wmsType = wmsType;
    }


    @Column(name = "WMS_STATUS")
    public String getWmsStatus() {
        return wmsStatus;
    }



    public void setWmsStatus(String wmsStatus) {
        this.wmsStatus = wmsStatus;
    }


    @Column(name = "PRESTOWAGE_NO")
    public String getPrestowageNo() {
        return prestowageNo;
    }



    public void setPrestowageNo(String prestowageNo) {
        this.prestowageNo = prestowageNo;
    }


    @Column(name = "GROSS_WT")
    public BigDecimal getGrossWt() {
        return grossWt;
    }



    public void setGrossWt(BigDecimal grossWt) {
        this.grossWt = grossWt;
    }


    @Column(name = "NET_WT")
    public BigDecimal getNetWt() {
        return netWt;
    }



    public void setNetWt(BigDecimal netWt) {
        this.netWt = netWt;
    }


    @Column(name = "PACK_NO")
    public Integer getPackNo() {
        return packNo;
    }



    public void setPackNo(Integer packNo) {
        this.packNo = packNo;
    }


    @Column(name = "WRAP_TYPE")
    public String getWrapType() {
        return wrapType;
    }



    public void setWrapType(String wrapType) {
        this.wrapType = wrapType;
    }


    @Column(name = "LICENSE_PLATE_NUMBER")
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }



    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }


    @Column(name = "IS_TO_MODIFY")
    public Boolean getIsToModify() {
        return isToModify;
    }



    public void setIsToModify(Boolean isToModify) {
        this.isToModify = isToModify;
    }


    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }


    @Column(name = "MAIN_WH_ID")
    public Long getMainWhId() {
        return mainWhId;
    }



    public void setMainWhId(Long mainWhId) {
        this.mainWhId = mainWhId;
    }


    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "CREATE_ID")
    public Long getCreateId() {
        return createId;
    }



    public void setCreateId(Long createId) {
        this.createId = createId;
    }


    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }



    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }



    public void setVersion(Integer version) {
        this.version = version;
    }


    
    public void setId(Long id) {
        this.id = id;
    }
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_CUSTOMS_DECLARATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }


    @Column(name = "IS_LOADING")
    public Integer getIsLoading() {
        return isLoading;
    }



    public void setIsLoading(Integer isLoading) {
        this.isLoading = isLoading;
    }



    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }



    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }


    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }



    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "DECLARATION_STATUS")
    public Integer getDeclarationStatus() {
        return declarationStatus;
    }

    public void setDeclarationStatus(Integer declarationStatus) {
        this.declarationStatus = declarationStatus;
    }
}
