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
package com.jumbo.wms.model.vmi.coachData;

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

@Entity
@Table(name = "T_COACH_PRODUCT_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CoachProductData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6261512526696723732L;
    private Long id;

    private String defaultAlias;

    private String style;

    private String styleDesc;

    private String color;

    private String colorDesc;

    private String styleColor;

    private String size;

    private String department;

    private String departmentDesc;

    private String classs;

    private String deptClass;

    private String classDesc;

    private String subClass;

    private String deptClassSub;

    private String subClassDesc;

    private String styleIntroDate;

    private String styleDeleteDate;

    private String introDate;

    private String deleteDate;

    private Integer rtlPrice;

    private String rtlCurrencyCode;

    private String colorGroupDesc;

    private String masterCollection;

    private String collectionDesc;

    private String subCollection;

    private String subcollectionDesc;

    private String hardwareColor;

    private String hardwareColorDesc;

    private String material;

    private String materialDesc;

    private String materialGroup;

    private String materialGroupDesc;

    private String silhouette;

    private String silhouetteDesc;

    private String signatureType;

    private String signatureTypeDesc;

    private String gender;

    private String genderDesc;

    private String closureDesc;

    private String planExclusion;

    private String status;

    private String specialProduct;

    private String specialProductDesc;

    private String handbagSize;

    private String dimLengthCM;

    private String dimHeightCM;

    private String dimWidthCM;

    private String parentStyle;

    private String styleGroup;

    private String essChannelExclusive;

    private String styleFsintroDate;

    private String styleFsdeleteDate;

    private String factoryType;

    private String countryCode;

    private String sourceCode;

    private String brand;

    private String UPCCode;

    private Date createTime;

    private Integer MasterStatus;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_COACH_PRODUCT_DATA", sequenceName = "S_T_COACH_PRODUCT_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COACH_PRODUCT_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DEFAULT_ALIAS", length = 72)
    public String getDefaultAlias() {
        return defaultAlias;
    }

    public void setDefaultAlias(String defaultAlias) {
        this.defaultAlias = defaultAlias;
    }

    @Column(name = "STYLE", length = 10)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "STYLE_DESC", length = 40)
    public String getStyleDesc() {
        return styleDesc;
    }

    public void setStyleDesc(String styleDesc) {
        this.styleDesc = styleDesc;
    }

    @Column(name = "COLOR", length = 5)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "COLOR_DESC", length = 30)
    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }

    @Column(name = "STYLE_COLOR", length = 20)
    public String getStyleColor() {
        return styleColor;
    }

    public void setStyleColor(String styleColor) {
        this.styleColor = styleColor;
    }

    @Column(name = "MASTER_SIZE", length = 5)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "DEPARTMENT", length = 10)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "DEPARTMENT_DESC", length = 30)
    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    @Column(name = "CLASS", length = 3)
    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    @Column(name = "DEPT_CLASS", length = 6)
    public String getDeptClass() {
        return deptClass;
    }

    public void setDeptClass(String deptClass) {
        this.deptClass = deptClass;
    }

    @Column(name = "CLASS_DESC", length = 23)
    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    @Column(name = "SUB_CLASS", length = 3)
    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    @Column(name = "DEPT_CLASS_SUB", length = 9)
    public String getDeptClassSub() {
        return deptClassSub;
    }

    public void setDeptClassSub(String deptClassSub) {
        this.deptClassSub = deptClassSub;
    }

    @Column(name = "SUB_CLASS_DESC", length = 22)
    public String getSubClassDesc() {
        return subClassDesc;
    }

    public void setSubClassDesc(String subClassDesc) {
        this.subClassDesc = subClassDesc;
    }

    @Column(name = "STYLE_INTRO_DATE", length = 13)
    public String getStyleIntroDate() {
        return styleIntroDate;
    }

    public void setStyleIntroDate(String styleIntroDate) {
        this.styleIntroDate = styleIntroDate;
    }

    @Column(name = "STYLE_DELETE_DATE", length = 15)
    public String getStyleDeleteDate() {
        return styleDeleteDate;
    }

    public void setStyleDeleteDate(String styleDeleteDate) {
        this.styleDeleteDate = styleDeleteDate;
    }

    @Column(name = "INTRO_DATE", length = 13)
    public String getIntroDate() {
        return introDate;
    }

    public void setIntroDate(String introDate) {
        this.introDate = introDate;
    }

    @Column(name = "DELETE_DATE", length = 15)
    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Column(name = "RTL_PRICE", length = 12)
    public Integer getRtlPrice() {
        return rtlPrice;
    }

    public void setRtlPrice(Integer rtlPrice) {
        this.rtlPrice = rtlPrice;
    }

    @Column(name = "RTL_CURRENCY_CODE", length = 3)
    public String getRtlCurrencyCode() {
        return rtlCurrencyCode;
    }

    public void setRtlCurrencyCode(String rtlCurrencyCode) {
        this.rtlCurrencyCode = rtlCurrencyCode;
    }

    @Column(name = "COLOR_GROUP_DESC", length = 14)
    public String getColorGroupDesc() {
        return colorGroupDesc;
    }

    public void setColorGroupDesc(String colorGroupDesc) {
        this.colorGroupDesc = colorGroupDesc;
    }

    @Column(name = "COLLECTION", length = 3)
    public String getMasterCollection() {
        return masterCollection;
    }

    public void setMasterCollection(String masterCollection) {
        this.masterCollection = masterCollection;
    }

    @Column(name = "COLLECTION_DESC", length = 26)
    public String getCollectionDesc() {
        return collectionDesc;
    }

    public void setCollectionDesc(String collectionDesc) {
        this.collectionDesc = collectionDesc;
    }

    @Column(name = "SUBCOLLECTION", length = 3)
    public String getSubCollection() {
        return subCollection;
    }

    public void setSubCollection(String subCollection) {
        this.subCollection = subCollection;
    }

    @Column(name = "SUBCOLLECTION_DESC", length = 50)
    public String getSubcollectionDesc() {
        return subcollectionDesc;
    }

    public void setSubcollectionDesc(String subcollectionDesc) {
        this.subcollectionDesc = subcollectionDesc;
    }

    @Column(name = "HARDWARE_COLOR", length = 10)
    public String getHardwareColor() {
        return hardwareColor;
    }

    public void setHardwareColor(String hardwareColor) {
        this.hardwareColor = hardwareColor;
    }

    @Column(name = "HARDWARE_COLOR_DESC", length = 20)
    public String getHardwareColorDesc() {
        return hardwareColorDesc;
    }

    public void setHardwareColorDesc(String hardwareColorDesc) {
        this.hardwareColorDesc = hardwareColorDesc;
    }

    @Column(name = "MATERIAL", length = 3)
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Column(name = "MATERIAL_DESC", length = 22)
    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    @Column(name = "MATERIAL_GROUP", length = 3)
    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    @Column(name = "MATERIAL_GROUP_DESC", length = 18)
    public String getMaterialGroupDesc() {
        return materialGroupDesc;
    }

    public void setMaterialGroupDesc(String materialGroupDesc) {
        this.materialGroupDesc = materialGroupDesc;
    }

    @Column(name = "SILHOUETTE", length = 3)
    public String getSilhouette() {
        return silhouette;
    }

    public void setSilhouette(String silhouette) {
        this.silhouette = silhouette;
    }

    @Column(name = "SILHOUETTE_DESC", length = 26)
    public String getSilhouetteDesc() {
        return silhouetteDesc;
    }

    public void setSilhouetteDesc(String silhouetteDesc) {
        this.silhouetteDesc = silhouetteDesc;
    }

    @Column(name = "SIGNATURE_TYPE", length = 10)
    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    @Column(name = "SIGNATURE_TYPE_DESC", length = 30)
    public String getSignatureTypeDesc() {
        return signatureTypeDesc;
    }

    public void setSignatureTypeDesc(String signatureTypeDesc) {
        this.signatureTypeDesc = signatureTypeDesc;
    }

    @Column(name = "GENDER", length = 3)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "GENDER_DESC", length = 6)
    public String getGenderDesc() {
        return genderDesc;
    }

    public void setGenderDesc(String genderDesc) {
        this.genderDesc = genderDesc;
    }

    @Column(name = "CLOSURE_DESC", length = 200)
    public String getClosureDesc() {
        return closureDesc;
    }

    public void setClosureDesc(String closureDesc) {
        this.closureDesc = closureDesc;
    }

    @Column(name = "PLAN_EXCLUSION", length = 25)
    public String getPlanExclusion() {
        return planExclusion;
    }

    public void setPlanExclusion(String planExclusion) {
        this.planExclusion = planExclusion;
    }

    @Column(name = "STATUS", length = 27)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "SPECIAL_PRODUCT", length = 3)
    public String getSpecialProduct() {
        return specialProduct;
    }

    public void setSpecialProduct(String specialProduct) {
        this.specialProduct = specialProduct;
    }

    @Column(name = "SPECIAL_PRODUCT_DESC", length = 23)
    public String getSpecialProductDesc() {
        return specialProductDesc;
    }

    public void setSpecialProductDesc(String specialProductDesc) {
        this.specialProductDesc = specialProductDesc;
    }

    @Column(name = "HANDBAG_SIZE", length = 11)
    public String getHandbagSize() {
        return handbagSize;
    }

    public void setHandbagSize(String handbagSize) {
        this.handbagSize = handbagSize;
    }

    @Column(name = "DIM_LENGTH_CM", length = 50)
    public String getDimLengthCM() {
        return dimLengthCM;
    }

    public void setDimLengthCM(String dimLengthCM) {
        this.dimLengthCM = dimLengthCM;
    }

    @Column(name = "DIM_HEIGHT_CM", length = 50)
    public String getDimHeightCM() {
        return dimHeightCM;
    }

    public void setDimHeightCM(String dimHeightCM) {
        this.dimHeightCM = dimHeightCM;
    }

    @Column(name = "DIM_WIDTH_CM", length = 50)
    public String getDimWidthCM() {
        return dimWidthCM;
    }

    public void setDimWidthCM(String dimWidthCM) {
        this.dimWidthCM = dimWidthCM;
    }

    @Column(name = "PARENT_STYLE", length = 4)
    public String getParentStyle() {
        return parentStyle;
    }

    public void setParentStyle(String parentStyle) {
        this.parentStyle = parentStyle;
    }

    @Column(name = "STYLE_GROUP", length = 47)
    public String getStyleGroup() {
        return styleGroup;
    }

    public void setStyleGroup(String styleGroup) {
        this.styleGroup = styleGroup;
    }

    @Column(name = "ESS_CHANNEL_EXCLUSIVE", length = 31)
    public String getEssChannelExclusive() {
        return essChannelExclusive;
    }

    public void setEssChannelExclusive(String essChannelExclusive) {
        this.essChannelExclusive = essChannelExclusive;
    }

    @Column(name = "STYLE_FSINTRO_DATE", length = 15)
    public String getStyleFsintroDate() {
        return styleFsintroDate;
    }

    public void setStyleFsintroDate(String styleFsintroDate) {
        this.styleFsintroDate = styleFsintroDate;
    }

    @Column(name = "STYLE_FSDELETE_DATE", length = 15)
    public String getStyleFsdeleteDate() {
        return styleFsdeleteDate;
    }

    public void setStyleFsdeleteDate(String styleFsdeleteDate) {
        this.styleFsdeleteDate = styleFsdeleteDate;
    }

    @Column(name = "FACTORY_TYPE", length = 37)
    public String getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(String factoryType) {
        this.factoryType = factoryType;
    }

    @Column(name = "COUNTRY_CODE", length = 3)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "SOURCE_CODE", length = 30)
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Column(name = "BRAND", length = 5)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "UPCCode", length = 18)
    public String getUPCCode() {
        return UPCCode;
    }

    public void setUPCCode(String uPCCode) {
        UPCCode = uPCCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "MASTER_STATUS", length = 2)
    public Integer getMasterStatus() {
        return MasterStatus;
    }

    public void setMasterStatus(Integer masterStatus) {
        MasterStatus = masterStatus;
    }



}
