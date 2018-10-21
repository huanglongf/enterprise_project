package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class RmiSku implements Serializable {

    private static final long serialVersionUID = 2970583582877230441L;

    private Long id;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * SKU编码
     */
    private String customerSkuCode;
    /**
     * 供应商商品编码
     */
    private String supplierCode;
    /**
     * Sku条码，唯一标示Sku的编码
     */
    private String barCode;
    /**
     * Product中JMCode
     */
    private String jmCode;
    /**
     * 扩展属性
     */
    private String keyProperties;
    /**
     * Sku名称，一般来说这个信息=Product的名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 其他用于唯一标示Sku的编码:官方商城,淘宝...
     */
    private String extensionCode1;
    /**
     * 其他用于唯一标示Sku的编码:品牌信息
     */
    private String extensionCode2;
    /**
     * 其他用于唯一标示Sku的编码
     */
    private String extensionCode3;
    /**
     * 颜色编码
     */
    private String color;
    /**
     * 颜色中文描述
     */
    private String colorName;
    /**
     * 尺码
     */
    private String skuSize;
    /**
     * 品牌编码
     */
    private String brandCode;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品长度（mm）
     */
    private BigDecimal length;
    /**
     * 商品宽度（mm）
     */
    private BigDecimal width;

    /**
     * 商品高度（mm）
     */
    private BigDecimal height;
    /**
     * 是否是sn商品
     */
    private Boolean isSnSku;
    /**
     * 商品毛重（kg）
     */
    private BigDecimal grossWeight;
    /**
     * 商品整箱件数
     */
    private Long boxQty;
    /**
     * 是否赠品
     */
    private Boolean isGift = Boolean.FALSE;
    /**
     * 是否液体、锂电池
     */
    private Boolean isRailway;
    /**
     * 保修卡类型
     */
    private Integer warrantyCardType;
    /**
     * 商品销售模式
     */
    private Integer salesMode;
    /**
     * 商品存放
     */
    private Integer stroeMode;
    /**
     * 有效期天数
     */
    private Integer validDate;
    /**
     * 预警天数
     */
    private Integer warningDate;
    /**
     * 预警天数LV1
     */
    private Integer warningDateLv1;
    /**
     * 预警天数LV2
     */
    private Integer warningDateLv2;
    /**
     * 附加条码
     */
    private List<String> addBarcodes;
    /**
     * 时间类型
     */
    private Integer timeType;
    /**
     * 商品吊牌价
     */
    private BigDecimal listPrice;

    /**
     * 店铺
     */
    private String owner;
    /**
     * 其他用于唯一标示Sku的编码:官方商城,淘宝...
     */
    private String skuExtCode1;
    /**
     * 最小过期时间
     */
    private Date minExpDate;
    /**
     * 最大过期时间
     */
    private Date maxExpDate;
    /**
     * 最小生产日期
     */
    private Date minProductDate;
    /**
     * 最大生产日期
     */
    private Date maxProductDate;

    /**
     * 商品类型
     */
    private Integer skuType;

    /**
     * 原产地
     */
    private String countryOfOrigin;

    private String category;
    /**
     * 子SN号数量
     */
    private Integer childSnQty;

    /**
     * 是否为耗材
     */
    private Boolean isConsumable;

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getValidDate() {
        return validDate;
    }

    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    public Integer getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(Integer warningDate) {
        this.warningDate = warningDate;
    }

    public Integer getWarningDateLv1() {
        return warningDateLv1;
    }

    public void setWarningDateLv1(Integer warningDateLv1) {
        this.warningDateLv1 = warningDateLv1;
    }

    public Integer getWarningDateLv2() {
        return warningDateLv2;
    }

    public void setWarningDateLv2(Integer warningDateLv2) {
        this.warningDateLv2 = warningDateLv2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerSkuCode() {
        return customerSkuCode;
    }

    public void setCustomerSkuCode(String customerSkuCode) {
        this.customerSkuCode = customerSkuCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getExtensionCode1() {
        return extensionCode1;
    }

    public void setExtensionCode1(String extensionCode1) {
        this.extensionCode1 = extensionCode1;
    }

    public String getExtensionCode2() {
        return extensionCode2;
    }

    public void setExtensionCode2(String extensionCode2) {
        this.extensionCode2 = extensionCode2;
    }

    public String getExtensionCode3() {
        return extensionCode3;
    }

    public void setExtensionCode3(String extensionCode3) {
        this.extensionCode3 = extensionCode3;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public Boolean getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Boolean isSnSku) {
        this.isSnSku = isSnSku;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Long getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Long boxQty) {
        this.boxQty = boxQty;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Boolean getIsRailway() {
        return isRailway;
    }

    public void setIsRailway(Boolean isRailway) {
        this.isRailway = isRailway;
    }

    public Integer getWarrantyCardType() {
        return warrantyCardType;
    }

    public void setWarrantyCardType(Integer warrantyCardType) {
        this.warrantyCardType = warrantyCardType;
    }

    public Integer getSalesMode() {
        return salesMode;
    }

    public void setSalesMode(Integer salesMode) {
        this.salesMode = salesMode;
    }

    public List<String> getAddBarcodes() {
        return addBarcodes;
    }

    public void setAddBarcodes(List<String> addBarcodes) {
        this.addBarcodes = addBarcodes;
    }

    public Integer getStroeMode() {
        return stroeMode;
    }

    public void setStroeMode(Integer stroeMode) {
        this.stroeMode = stroeMode;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSkuExtCode1() {
        return skuExtCode1;
    }

    public void setSkuExtCode1(String skuExtCode1) {
        this.skuExtCode1 = skuExtCode1;
    }

    public Date getMinExpDate() {
        return minExpDate;
    }

    public void setMinExpDate(Date minExpDate) {
        this.minExpDate = minExpDate;
    }

    public Date getMaxExpDate() {
        return maxExpDate;
    }

    public void setMaxExpDate(Date maxExpDate) {
        this.maxExpDate = maxExpDate;
    }

    public Date getMinProductDate() {
        return minProductDate;
    }

    public void setMinProductDate(Date minProductDate) {
        this.minProductDate = minProductDate;
    }

    public Date getMaxProductDate() {
        return maxProductDate;
    }

    public void setMaxProductDate(Date maxProductDate) {
        this.maxProductDate = maxProductDate;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsConsumable() {
        return isConsumable;
    }

    public void setIsConsumable(Boolean isConsumable) {
        this.isConsumable = isConsumable;
    }

    public Integer getChildSnQty() {
        return childSnQty;
    }

    public void setChildSnQty(Integer childSnQty) {
        this.childSnQty = childSnQty;
    }


}
