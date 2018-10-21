package com.jumbo.wms.model.hub2wms;


import java.util.Map;

import com.jumbo.wms.model.BaseModel;

public class AgvSkuToHub  extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = -4094267261579346818L;
    
    private String skuCode;
    
    private String skuNumber;
    
    private String name;
    
    private Boolean virtual=new Boolean(false);
    
    private Boolean enableBatch=new Boolean(false);
    
    private Float price;
    
    private Float length;
    
    private Float width;
    
    private Float height;
    
    private Float weight;
    
    private String brandName;
    
    private String category;
    
    private String specification;
    
    private Boolean breakable=new Boolean(false);
    
    private Boolean heavy=new Boolean(false);
    
    private Map<String,String> extendedProperties;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Boolean getEnableBatch() {
        return enableBatch;
    }

    public void setEnableBatch(Boolean enableBatch) {
        this.enableBatch = enableBatch;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Boolean getBreakable() {
        return breakable;
    }

    public void setBreakable(Boolean breakable) {
        this.breakable = breakable;
    }

    public Boolean getHeavy() {
        return heavy;
    }

    public void setHeavy(Boolean heavy) {
        this.heavy = heavy;
    }

    public Map<String, String> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(Map<String, String> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }
    
    
}
