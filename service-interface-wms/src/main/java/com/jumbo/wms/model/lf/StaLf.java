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

package com.jumbo.wms.model.lf;

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
 * 利丰单据附加信息头表
 * 
 * @author bin.hu
 * 
 */
@Entity
@Table(name = "T_WH_STA_LF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaLf extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = 2140530638499458373L;

    /**
     * PK
     */
    private Long id;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 运输方式
     */
    private String transportMode;

    /**
     * 单据类型
     */
    private String orderType;

    /**
     * PACK SLIP NO
     */
    private String packSlipNo;

    /**
     * 运输时效
     */
    private String transportPra;

    /**
     * NFS店铺编码
     */
    private String nfsStoreCode;

    /**
     * 城市
     */
    private String city;

    /**
     * 邮政编码
     */
    private String zip;

    /**
     * 地址1
     */
    private String address1;

    /**
     * 地址2
     */
    private String address2;

    /**
     * 地址3
     */
    private String address3;

    /**
     * 地址4
     */
    private String address4;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * Nike PO
     */
    private String nikePo;

    /**
     * VAS Code
     */
    private String vasCode;

    /**
     * Division Code
     */
    private String divisionCode;

    /**
     * Division Code转译
     */
    private String divisionCodeTranslation;

    /**
     * CRD时间
     */
    private String crd;

    /**
     * 仓库ID
     */
    private Long ouId;
    
    /**
     * 发货方式
     */
    private String transMethod;
    
    /**
     * 是否多仓发货
     */
    private Boolean isMoreWh;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_STA_LF", sequenceName = "S_T_WH_STA_LF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_STA_LF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "TRANSPORT_MODE")
    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    @Column(name = "ORDER_TYPE")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "PACK_SLIP_NO")
    public String getPackSlipNo() {
        return packSlipNo;
    }

    public void setPackSlipNo(String packSlipNo) {
        this.packSlipNo = packSlipNo;
    }

    @Column(name = "TRANSPORT_PRA")
    public String getTransportPra() {
        return transportPra;
    }

    public void setTransportPra(String transportPra) {
        this.transportPra = transportPra;
    }

    @Column(name = "NFS_STORE_CODE")
    public String getNfsStoreCode() {
        return nfsStoreCode;
    }

    public void setNfsStoreCode(String nfsStoreCode) {
        this.nfsStoreCode = nfsStoreCode;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "ZIP")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name = "ADDRESS1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "ADDRESS2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "ADDRESS3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Column(name = "ADDRESS4")
    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    @Column(name = "COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "NIKE_PO")
    public String getNikePo() {
        return nikePo;
    }

    public void setNikePo(String nikePo) {
        this.nikePo = nikePo;
    }

    @Column(name = "VAS_CODE")
    public String getVasCode() {
        return vasCode;
    }

    public void setVasCode(String vasCode) {
        this.vasCode = vasCode;
    }

    @Column(name = "DIVISION_CODE")
    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    @Column(name = "DIVISION_CODE_TRANSLATION")
    public String getDivisionCodeTranslation() {
        return divisionCodeTranslation;
    }

    public void setDivisionCodeTranslation(String divisionCodeTranslation) {
        this.divisionCodeTranslation = divisionCodeTranslation;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "CRD")
    public String getCrd() {
        return crd;
    }

    public void setCrd(String crd) {
        this.crd = crd;
    }

    @Column(name = "TRANS_METHOD")
	public String getTransMethod() {
		return transMethod;
	}

	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}

	@Column(name = "IS_MORE_WH")
	public Boolean getIsMoreWh() {
		return isMoreWh;
	}

	public void setIsMoreWh(Boolean isMoreWh) {
		this.isMoreWh = isMoreWh;
	}

    

}
