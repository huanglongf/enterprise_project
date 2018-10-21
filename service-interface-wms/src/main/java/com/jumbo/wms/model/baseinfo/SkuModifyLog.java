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

package com.jumbo.wms.model.baseinfo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.TimeTypeMode;
import com.jumbo.wms.model.warehouse.TransDeliveryType;

/**
 * SkuModifyLog
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_BI_INV_SKU_MODIFY_LOG")
public class SkuModifyLog extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -4852752326335882170L;

    public SkuModifyLog() {
        super();
    }

    public SkuModifyLog(Long id) {
        super();
        this.id = id;
    }

    /**
     * PK
     */
    private Long id;

    /**
     * sku
     */
    private Sku sku;
    /**
     * 客户商品编码
     */
    private String customerSkuCode;
    /**
     * 客户
     */
    private Customer customer;
    /**
     * 编码，唯一标示Sku的编码
     */
    private String code;
    /**
     * 供应商商品编码(货号)
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
     * version
     */
    private int version;
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
     * 品牌
     */
    private Brand brand;
    /**
     * 合理纸箱包装
     */
    private SkuModifyLog paperSku;
    /**
     * 商品销售模式
     */
    private SkuSalesModel salesMode;

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
     * 运送类型
     */
    private TransDeliveryType deliveryType;

    /**
     * 存放模式
     */
    private InboundStoreMode storemode = InboundStoreMode.TOGETHER;
    /**
     * 商品分类Id
     */
    private Long skuCategoriesId;
    /**
     * 保修卡类型
     */
    private SkuWarrantyCardType warrantyCardType;
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
     * 时间类型
     */
    private TimeTypeMode timeType;
    /**
     * 商品吊牌价
     */
    private BigDecimal listPrice;
    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    private Long opUserId;// 操作人Id

    @Column(name = "op_user_id")
    public Long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Long opUserId) {
        this.opUserId = opUserId;
    }

    @Column(name = "list_price")
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU", sequenceName = "S_T_BI_INV_SKU_MODIFY_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 150)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 300)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "EN_NAME", length = 300)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Transient
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "EXT_CODE1", length = 150)
    public String getExtensionCode1() {
        return extensionCode1;
    }

    public void setExtensionCode1(String extensionCode1) {
        this.extensionCode1 = extensionCode1;
    }

    @Column(name = "EXT_CODE2", length = 150)
    public String getExtensionCode2() {
        return extensionCode2;
    }

    public void setExtensionCode2(String extensionCode2) {
        this.extensionCode2 = extensionCode2;
    }

    @Column(name = "EXT_CODE3", length = 150)
    public String getExtensionCode3() {
        return extensionCode3;
    }

    public void setExtensionCode3(String extensionCode3) {
        this.extensionCode3 = extensionCode3;
    }

    @Column(name = "BAR_CODE", length = 100)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "KEY_PROPERTIES", length = 100)
    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    @Column(name = "SUPPLIER_CODE", length = 100)
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "JM_CODE", length = 255)
    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BRAND_ID")
    @Index(name = "IDX_BRAND_ID")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Column(name = "COLOR", length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "COLOR_NAME", length = 50)
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Column(name = "SKU_SIZE", length = 50)
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPER_SKU_ID")
    @Index(name = "IDX_BI_SKU_ID")
    public SkuModifyLog getPaperSku() {
        return paperSku;
    }

    public void setPaperSku(SkuModifyLog paperSku) {
        this.paperSku = paperSku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @Index(name = "IDX_WHC_CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "WIDTH")
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @Column(name = "HEIGHT")
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Column(name = "[LENGTH]")
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    @Column(name = "IS_SN_SKU")
    public Boolean getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Boolean isSnSku) {
        this.isSnSku = isSnSku;
    }

    @Column(name = "SALES_MODEL", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSalesModel")})
    public SkuSalesModel getSalesMode() {
        return salesMode;
    }

    public void setSalesMode(SkuSalesModel salesMode) {
        this.salesMode = salesMode;
    }

    @Column(name = "GROSS_WEIGHT")
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Column(name = "BOX_QTY")
    public Long getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Long boxQty) {
        this.boxQty = boxQty;
    }

    @Column(name = "STORE_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InboundStoreMode")})
    public InboundStoreMode getStoremode() {
        return storemode;
    }

    public void setStoremode(InboundStoreMode storemode) {
        this.storemode = storemode;
    }

    @Column(name = "IS_GIFT")
    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    @Column(name = "IS_RAILWAY", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransDeliveryType")})
    public TransDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(TransDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Column(name = "SKU_CATEGORIES_ID")
    @Index(name = "IDX_ISKU_CATEGORIES_ID")
    public Long getSkuCategoriesId() {
        return skuCategoriesId;
    }

    public void setSkuCategoriesId(Long skuCategoriesId) {
        this.skuCategoriesId = skuCategoriesId;
    }

    @Column(name = "WARRANTY_CARD_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuWarrantyCardType")})
    public SkuWarrantyCardType getWarrantyCardType() {
        return warrantyCardType;
    }

    public void setWarrantyCardType(SkuWarrantyCardType warrantyCardType) {
        this.warrantyCardType = warrantyCardType;
    }

    @Column(name = "customer_sku_code", length = 50)
    public String getCustomerSkuCode() {
        return customerSkuCode;
    }

    public void setCustomerSkuCode(String customerSkuCode) {
        this.customerSkuCode = customerSkuCode;
    }

    @Column(name = "VALID_DATE")
    public Integer getValidDate() {
        return validDate;
    }

    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    @Column(name = "WARNING_DATE")
    public Integer getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(Integer warningDate) {
        this.warningDate = warningDate;
    }

    @Column(name = "warning_date_lv1")
    public Integer getWarningDateLv1() {
        return warningDateLv1;
    }

    public void setWarningDateLv1(Integer warningDateLv1) {
        this.warningDateLv1 = warningDateLv1;
    }

    @Column(name = "warning_date_lv2")
    public Integer getWarningDateLv2() {
        return warningDateLv2;
    }

    public void setWarningDateLv2(Integer warningDateLv2) {
        this.warningDateLv2 = warningDateLv2;
    }

    @Column(name = "TIME_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TimeTypeMode")})
    public TimeTypeMode getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeTypeMode timeType) {
        this.timeType = timeType;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }



}
