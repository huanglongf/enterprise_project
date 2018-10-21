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
package com.jumbo.wms.model.vmi.cch;

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
@Table(name = "T_CACHE_PRODUCT_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CacheProductData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6261512526696723732L;
    private Long id;

    private String itemsBarcode;

    private String skuCode;

    private String productCode;

    private String colorCode;

    private String productName;

    private String description;

    private String colorName;

    private String cnSize;

    private String familyCn;

    private String isp;

    private String standardNumber;

    private String gbStandard;

    private String carelable11;

    private String carelable12;

    private String carelable13;

    private String carelable14;

    private String carelable15;

    private String madeIn;

    private String mainfabricComposition;

    private String liningComposition;

    private String paddingComposition;

    private Date createTime;

    private String status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CACHE_PRODUCT_DATA", sequenceName = "S_T_CACHE_PRODUCT_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CACHE_PRODUCT_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ITEMS_BARCODE", length = 30)
    public String getItemsBarcode() {
        return itemsBarcode;
    }

    public void setItemsBarcode(String itemsBarcode) {
        this.itemsBarcode = itemsBarcode;
    }

    @Column(name = "SKU_CODE", length = 20)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "PRODUCT_CODE", length = 8)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "COLOR_CODE", length = 5)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "PRODUCT_NAME", length = 25)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "DESCRIPTION", length = 40)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "COLOR_NAME", length = 30)
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Column(name = "CN_SIZE", length = 15)
    public String getCnSize() {
        return cnSize;
    }

    public void setCnSize(String cnSize) {
        this.cnSize = cnSize;
    }

    @Column(name = "FAMILY_CN", length = 40)
    public String getFamilyCn() {
        return familyCn;
    }

    public void setFamilyCn(String familyCn) {
        this.familyCn = familyCn;
    }

    @Column(name = "ISP")
    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    @Column(name = "STANDARD_NUMBER", length = 30)
    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    @Column(name = "GB_STANDARD", length = 30)
    public String getGbStandard() {
        return gbStandard;
    }

    public void setGbStandard(String gbStandard) {
        this.gbStandard = gbStandard;
    }

    @Column(name = "CARELABE11", length = 5)
    public String getCarelable11() {
        return carelable11;
    }

    public void setCarelable11(String carelable11) {
        this.carelable11 = carelable11;
    }

    @Column(name = "CARELABE12", length = 5)
    public String getCarelable12() {
        return carelable12;
    }

    public void setCarelable12(String carelable12) {
        this.carelable12 = carelable12;
    }

    @Column(name = "CARELABE13", length = 30)
    public String getCarelable13() {
        return carelable13;
    }

    public void setCarelable13(String carelable13) {
        this.carelable13 = carelable13;
    }

    @Column(name = "CARELABE14", length = 30)
    public String getCarelable14() {
        return carelable14;
    }

    public void setCarelable14(String carelable14) {
        this.carelable14 = carelable14;
    }

    @Column(name = "CARELABE15", length = 30)
    public String getCarelable15() {
        return carelable15;
    }

    public void setCarelable15(String carelable15) {
        this.carelable15 = carelable15;
    }

    @Column(name = "MADE_IN", length = 3)
    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    @Column(name = "MAINFABRIC_COMPOSITION", length = 200)
    public String getMainfabricComposition() {
        return mainfabricComposition;
    }

    public void setMainfabricComposition(String mainfabricComposition) {
        this.mainfabricComposition = mainfabricComposition;
    }

    @Column(name = "LINING_COMPOSITION", length = 200)
    public String getLiningComposition() {
        return liningComposition;
    }

    public void setLiningComposition(String liningComposition) {
        this.liningComposition = liningComposition;
    }

    @Column(name = "PADDING_COMPOSITION", length = 200)
    public String getPaddingComposition() {
        return paddingComposition;
    }

    public void setPaddingComposition(String paddingComposition) {
        this.paddingComposition = paddingComposition;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
