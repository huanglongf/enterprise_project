package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MqSkuMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2654699311994576352L;

    /**
     * oms商品编码(到款)
     */
    private String jmCode;

    /**
     * 供应商商品编码(到款)
     */
    private String supplierSkuCode;

    /**
     * 条码(供应商到颜色尺码)
     */
    @Deprecated
    private String barCode;

    /**
     * 商品名
     */
    private String name;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * JM编码(到尺码,颜色)
     */
    @Deprecated
    private String jmskuCode;

    /**
     * sku唯一标识(到尺码,颜色)
     */
    private String extentionCode;

    /**
     * 颜色,尺码
     */
    private String keyProperties;

    /**
     * 季节相关属性
     */
    private String seasonJSON;

    /**
     * 区
     */
    private String division;

    /**
     * 颜色编码
     */
    private String color;

    /**
     * 尺码
     */
    private String skuSize;

    /**
     * 商品动态属性 json格式：
     * {"mic_busgrp":"Hardware","mic_isphys":"TRUE","mic_revsumdiv":"Accessories","mic_mediatype"
     * :"Download","mic_issoft":"FALSE","mic_prodfam":"MsFam3","mic_ismsft":"TRUE"}
     */
    private String extProps;
    
    /**
     * 店铺ID
     */
    private Long omsShopId;
    
    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 店铺名称
     */
    private String shopName;

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getJmskuCode() {
        return jmskuCode;
    }

    public void setJmskuCode(String jmskuCode) {
        this.jmskuCode = jmskuCode;
    }

    public String getExtentionCode() {
        return extentionCode;
    }

    public void setExtentionCode(String extentionCode) {
        this.extentionCode = extentionCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getSeasonJSON() {
        return seasonJSON;
    }

    public void setSeasonJSON(String seasonJSON) {
        this.seasonJSON = seasonJSON;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getExtProps() {
        return extProps;
    }

    public void setExtProps(String extProps) {
        this.extProps = extProps;
    }

	public Long getOmsShopId() {
		return omsShopId;
	}

	public void setOmsShopId(Long omsShopId) {
		this.omsShopId = omsShopId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
