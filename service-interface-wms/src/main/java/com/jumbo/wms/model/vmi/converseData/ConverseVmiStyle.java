package com.jumbo.wms.model.vmi.converseData;

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


@Entity
@Table(name = "T_CONVERSE_STYLE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseVmiStyle extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3535772920691208736L;

    private Long id;

    private String styleId;

    private String skuNO;

    private String CHDescription;

    private String ENDescription;

    private String baseUint;

    private String itemCategory;

    private String pordectGroupCode;

    private BigDecimal retailPrice;

    private String color;

    private String enColorDes;

    private String chColorDes;

    private String styleSize;

    private String collection;

    private String season;

    private String theme;

    private String brandCode;

    private String genderCode;

    private String quotaCategory;

    private String prodClassCode;

    private String localFlag;

    private String smuFlag;

    private String marketingRegion;

    private String ageGroup;

    private String mfgProcess;

    private String consumerGroup;

    private String pfg;

    private String lifeCycleCode;

    private String cut;

    private String srNumber;

    private String upcCode;

    private BigDecimal wholeSalePrice;

    private String pictureName;

    private Date deliveryDate;

    private String eanCode;

    private Date version;

    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_STYLE", sequenceName = "S_T_CONVERSE_STYLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_STYLE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STYLEID", length = 20)
    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    @Column(name = "SKUNO", length = 23)
    public String getSkuNO() {
        return skuNO;
    }

    public void setSkuNO(String skuNO) {
        this.skuNO = skuNO;
    }

    @Column(name = "CHDESCRIPTION", length = 50)
    public String getCHDescription() {
        return CHDescription;
    }

    public void setCHDescription(String cHDescription) {
        CHDescription = cHDescription;
    }

    @Column(name = "ENDESCRIPTION", length = 50)
    public String getENDescription() {
        return ENDescription;
    }

    public void setENDescription(String eNDescription) {
        ENDescription = eNDescription;
    }

    @Column(name = "BASE_UINT", length = 10)
    public String getBaseUint() {
        return baseUint;
    }

    public void setBaseUint(String baseUint) {
        this.baseUint = baseUint;
    }

    @Column(name = "ITEM_CATEGORY", length = 20)
    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    @Column(name = "PORDECT_GROUP_CODE", length = 20)
    public String getPordectGroupCode() {
        return pordectGroupCode;
    }

    public void setPordectGroupCode(String pordectGroupCode) {
        this.pordectGroupCode = pordectGroupCode;
    }

    @Column(name = "RETAIL_PRICE")
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "COLOR", length = 10)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "EN_COLOR_DES", length = 30)
    public String getEnColorDes() {
        return enColorDes;
    }

    public void setEnColorDes(String enColorDes) {
        this.enColorDes = enColorDes;
    }

    @Column(name = "CH_COLOR_DES", length = 30)
    public String getChColorDes() {
        return chColorDes;
    }

    public void setChColorDes(String chColorDes) {
        this.chColorDes = chColorDes;
    }

    @Column(name = "STYLE_SIZE", length = 10)
    public String getStyleSize() {
        return styleSize;
    }

    public void setStyleSize(String styleSize) {
        this.styleSize = styleSize;
    }

    @Column(name = "COLLECTION", length = 10)
    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Column(name = "SEASON", length = 10)
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Column(name = "THEME", length = 10)
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Column(name = "BRAND_CODE", length = 10)
    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @Column(name = "GENDER_CODE", length = 10)
    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    @Column(name = "QUOTA_CATEGORY", length = 10)
    public String getQuotaCategory() {
        return quotaCategory;
    }

    public void setQuotaCategory(String quotaCategory) {
        this.quotaCategory = quotaCategory;
    }

    @Column(name = "PROD_CLASS_CODE", length = 10)
    public String getProdClassCode() {
        return prodClassCode;
    }

    public void setProdClassCode(String prodClassCode) {
        this.prodClassCode = prodClassCode;
    }

    @Column(name = "LOCAL_FLAG", length = 10)
    public String getLocalFlag() {
        return localFlag;
    }

    public void setLocalFlag(String localFlag) {
        this.localFlag = localFlag;
    }

    @Column(name = "SMU_FLAG", length = 10)
    public String getSmuFlag() {
        return smuFlag;
    }

    public void setSmuFlag(String smuFlag) {
        this.smuFlag = smuFlag;
    }

    @Column(name = "MARKETING_REGION", length = 10)
    public String getMarketingRegion() {
        return marketingRegion;
    }

    public void setMarketingRegion(String marketingRegion) {
        this.marketingRegion = marketingRegion;
    }

    @Column(name = "AGE_GROUP", length = 10)
    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Column(name = "MFG_PROCESS", length = 10)
    public String getMfgProcess() {
        return mfgProcess;
    }

    public void setMfgProcess(String mfgProcess) {
        this.mfgProcess = mfgProcess;
    }

    @Column(name = "CONSUMER_GROUP", length = 10)
    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    @Column(name = "PFG", length = 10)
    public String getPfg() {
        return pfg;
    }

    public void setPfg(String pfg) {
        this.pfg = pfg;
    }

    @Column(name = "LIFE_CYCLE_CODE", length = 10)
    public String getLifeCycleCode() {
        return lifeCycleCode;
    }

    public void setLifeCycleCode(String lifeCycleCode) {
        this.lifeCycleCode = lifeCycleCode;
    }

    @Column(name = "CUT", length = 10)
    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    @Column(name = "SR_NUMBER", length = 15)
    public String getSrNumber() {
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    @Column(name = "UPC_CODE", length = 15)
    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    @Column(name = "PICTURE_NAME", length = 15)
    public String getPictureName() {
        return pictureName;
    }

    @Column(name = "WHOLE_SALE_PRICE")
    public BigDecimal getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(BigDecimal wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    @Column(name = "DELIVERY_DATE")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column(name = "EAN_CODE", length = 20)
    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    @Column(name = "version")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "BATCH_NUM", length = 10)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }


}
