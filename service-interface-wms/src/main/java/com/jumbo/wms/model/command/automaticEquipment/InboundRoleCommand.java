package com.jumbo.wms.model.command.automaticEquipment;

import java.math.BigDecimal;

import com.jumbo.wms.model.automaticEquipment.InboundRole;

/**
 * 入库规则
 * 
 * @author lihui
 *
 * @createDate 2016年1月19日 下午7:21:46
 */
public class InboundRoleCommand extends InboundRole {

    private static final long serialVersionUID = 2307619914212798061L;

    private String owner;

    private Long ouId;

    private String ouName;

    private Long skuId;

    private String skuName;

    private String skuBarCode;

    private String skuCode;

    private Long skuTypeId;

    private String skuTypeName;

    private Long locationId;

    private String locationCode;

    private Long popUpAraeId;

    private String popUpAraeCode;

    private String channelName;

    private Long channelId;

    private String popUpAraeName;

    private String lvStr;

    private String skuSpCode;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal weight;

    private String skuCategoriesName;

    private String deliveryType;

    private String barCode;

    public String getSkuSpCode() {
        return skuSpCode;
    }


    public void setSkuSpCode(String skuSpCode) {
        this.skuSpCode = skuSpCode;
    }


    public Long getOuId() {
        return ouId;
    }


    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


    public String getOuName() {
        return ouName;
    }


    public void setOuName(String ouName) {
        this.ouName = ouName;
    }


    public Long getSkuId() {
        return skuId;
    }


    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }


    public String getSkuName() {
        return skuName;
    }


    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }


    public String getChannelName() {
        return channelName;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public Long getSkuTypeId() {
        return skuTypeId;
    }


    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }


    public String getSkuTypeName() {
        return skuTypeName;
    }


    public void setSkuTypeName(String skuTypeName) {
        this.skuTypeName = skuTypeName;
    }


    public Long getLocationId() {
        return locationId;
    }


    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public String getLocationCode() {
        return locationCode;
    }


    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }


    public Long getChannelId() {
        return channelId;
    }


    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }


    public String getSkuBarCode() {
        return skuBarCode;
    }


    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }


    public String getSkuCode() {
        return skuCode;
    }


    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


    public Long getPopUpAraeId() {
        return popUpAraeId;
    }


    public void setPopUpAraeId(Long popUpAraeId) {
        this.popUpAraeId = popUpAraeId;
    }


    public String getPopUpAraeCode() {
        return popUpAraeCode;
    }


    public void setPopUpAraeCode(String popUpAraeCode) {
        this.popUpAraeCode = popUpAraeCode;
    }


    public String getPopUpAraeName() {
        return popUpAraeName;
    }


    public void setPopUpAraeName(String popUpAraeName) {
        this.popUpAraeName = popUpAraeName;
    }


    public String getLvStr() {
        return lvStr;
    }


    public void setLvStr(String lvStr) {
        this.lvStr = lvStr;
    }


    public String getOwner() {
        return owner;
    }


    public BigDecimal getLength() {
        return length;
    }


    public void setLength(BigDecimal length) {
        this.length = length;
    }


    public BigDecimal getWidth() {
        return width;
    }


    public void setWidth(BigDecimal width) {
        this.width = width;
    }


    public BigDecimal getHeight() {
        return height;
    }


    public void setHeight(BigDecimal height) {
        this.height = height;
    }


    public BigDecimal getWeight() {
        return weight;
    }


    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }


    public String getSkuCategoriesName() {
        return skuCategoriesName;
    }


    public void setSkuCategoriesName(String skuCategoriesName) {
        this.skuCategoriesName = skuCategoriesName;
    }


    public String getDeliveryType() {
        return deliveryType;
    }


    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }


    public String getBarCode() {
        return barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }



}
