package com.jumbo.wms.model.vmi.converseData;

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
@Table(name = "T_CONVERSE_PRODUCT_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseProductInfo extends BaseModel {


    /**
	 * 
	 */
    private static final long serialVersionUID = 2546912885188930419L;

    private Long id;

    private String lineNO;

    private String bu;

    private String skuCode;

    private String cnName;

    private String enName;

    private String ifEvengreen;

    private String cnColor;

    private String enColor;

    private String cnMaterial;

    private String enMaterial;

    private String cnStyle;

    private String enStyle;

    private String cnCategory;

    private String enCategory;

    private String category2;

    private String cnStory;

    private String enStory;

    private String cnDescription;

    private String enDescription;

    private String cnGender;

    private String enGender;

    private String actualSize;

    private String retailPrice;

    private String launchDate;

    private String launchDate2;

    private String launchDate3;

    private String specialStore;

    private String primaryStyle;

    private String cnPage;

    private String isQtyMultipleOf6;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_PRODUCT_INFO", sequenceName = "S_T_CONVERSE_PRODUCT_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_PRODUCT_INFO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LINENO")
    public String getLineNO() {
        return lineNO;
    }

    public void setLineNO(String lineNO) {
        this.lineNO = lineNO;
    }

    @Column(name = "BU")
    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    @Column(name = "SKUCODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "CN_NAME")
    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Column(name = "EN_NAME")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "IF_EVENGREEN")
    public String getIfEvengreen() {
        return ifEvengreen;
    }

    public void setIfEvengreen(String ifEvengreen) {
        this.ifEvengreen = ifEvengreen;
    }

    @Column(name = "CN_COLOR")
    public String getCnColor() {
        return cnColor;
    }

    public void setCnColor(String cnColor) {
        this.cnColor = cnColor;
    }

    @Column(name = "EN_COLOR")
    public String getEnColor() {
        return enColor;
    }

    public void setEnColor(String enColor) {
        this.enColor = enColor;
    }

    @Column(name = "CN_MATERIAL")
    public String getCnMaterial() {
        return cnMaterial;
    }

    public void setCnMaterial(String cnMaterial) {
        this.cnMaterial = cnMaterial;
    }

    @Column(name = "EN_MATERIAL")
    public String getEnMaterial() {
        return enMaterial;
    }

    public void setEnMaterial(String enMaterial) {
        this.enMaterial = enMaterial;
    }

    @Column(name = "CN_STYLE")
    public String getCnStyle() {
        return cnStyle;
    }

    public void setCnStyle(String cnStyle) {
        this.cnStyle = cnStyle;
    }

    @Column(name = "EN_STYLE")
    public String getEnStyle() {
        return enStyle;
    }

    public void setEnStyle(String enStyle) {
        this.enStyle = enStyle;
    }

    @Column(name = "CN_CATEGORY")
    public String getCnCategory() {
        return cnCategory;
    }

    public void setCnCategory(String cnCategory) {
        this.cnCategory = cnCategory;
    }

    @Column(name = "EN_CATEGORY")
    public String getEnCategory() {
        return enCategory;
    }

    public void setEnCategory(String enCategory) {
        this.enCategory = enCategory;
    }

    @Column(name = "CATEGORY2")
    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    @Column(name = "CN_STORY")
    public String getCnStory() {
        return cnStory;
    }

    public void setCnStory(String cnStory) {
        this.cnStory = cnStory;
    }

    @Column(name = "EN_STORY")
    public String getEnStory() {
        return enStory;
    }

    public void setEnStory(String enStory) {
        this.enStory = enStory;
    }

    @Column(name = "CN_DESCRIPTION")
    public String getCnDescription() {
        return cnDescription;
    }

    public void setCnDescription(String cnDescription) {
        this.cnDescription = cnDescription;
    }

    @Column(name = "EN_DESCRIPTION")
    public String getEnDescription() {
        return enDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    @Column(name = "CN_GENDER")
    public String getCnGender() {
        return cnGender;
    }

    public void setCnGender(String cnGender) {
        this.cnGender = cnGender;
    }

    @Column(name = "EN_GENDER")
    public String getEnGender() {
        return enGender;
    }

    public void setEnGender(String enGender) {
        this.enGender = enGender;
    }

    @Column(name = "ACTUAL_SIZE")
    public String getActualSize() {
        return actualSize;
    }

    public void setActualSize(String actualSize) {
        this.actualSize = actualSize;
    }

    @Column(name = "RETAIL_PRICE")
    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "LAUNCHDATE")
    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    @Column(name = "LAUNCHDATE2")
    public String getLaunchDate2() {
        return launchDate2;
    }

    public void setLaunchDate2(String launchDate2) {
        this.launchDate2 = launchDate2;
    }

    @Column(name = "LAUNCHDATE3")
    public String getLaunchDate3() {
        return launchDate3;
    }

    public void setLaunchDate3(String launchDate3) {
        this.launchDate3 = launchDate3;
    }

    @Column(name = "SPECIAL_STORE")
    public String getSpecialStore() {
        return specialStore;
    }

    public void setSpecialStore(String specialStore) {
        this.specialStore = specialStore;
    }

    @Column(name = "PRIMARY_STYLE")
    public String getPrimaryStyle() {
        return primaryStyle;
    }

    public void setPrimaryStyle(String primaryStyle) {
        this.primaryStyle = primaryStyle;
    }

    @Column(name = "CN_PAGE")
    public String getCnPage() {
        return cnPage;
    }

    public void setCnPage(String cnPage) {
        this.cnPage = cnPage;
    }

    @Column(name = "IS_QTY_MULTIPLE_OF6")
    public String getIsQtyMultipleOf6() {
        return isQtyMultipleOf6;
    }

    public void setIsQtyMultipleOf6(String isQtyMultipleOf6) {
        this.isQtyMultipleOf6 = isQtyMultipleOf6;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }



}
