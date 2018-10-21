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

package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 外包仓商品中间表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_WH_THREEPL_SKU")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WarehouseMsgSku extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -4150016021815095485L;

    /**
     * 数据唯一标识
     */
    private Long id;

    /**
     * 对接唯一编码
     */
    private String code;

    /**
     * 商品条码
     */
    private String barcode;


    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品英文名称
     */
    private String enName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 尺码
     */
    private String skuSize;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 长(单位MM)
     */
    private BigDecimal length;

    /**
     * 宽(单位MM)
     */
    private BigDecimal width;

    /**
     * 高(单位MM)
     */
    private BigDecimal height;

    /**
     * 是否SN号管理商品
     */
    private Boolean isSn;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 接口方
     */
    private String source;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 创建时间
     */
    private Date crateTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 状态
     */
    private Long status;

    /**
     * 错误次数
     */
    private Long errorCount;

    /**
     * 反馈
     */
    private String msg;

    /**
     * 条码明细
     */
    private List<WarehouseMsgSkuBarcode> skuBarcode;

    /**
     * 商品客户编码
     */
    private String customer;

    /**
     * 外包仓商品编码
     */
    private String skuThreeplCode;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 外包仓数据唯一标识
     */
    private Long uuid;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_SKU", sequenceName = "S_T_WH_MSG_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "bar_code")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "en_name")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "sku_size")
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "length")
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    @Column(name = "width")
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @Column(name = "height")
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Column(name = "is_sn")
    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    @Column(name = "ext_memo")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "wh_code")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name = "carete_time")
    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "status")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "error_count")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "msgSku", orphanRemoval = true)
    public List<WarehouseMsgSkuBarcode> getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(List<WarehouseMsgSkuBarcode> skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    @Column(name = "customer")
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "SKU_THREEPL_CODE")
    public String getSkuThreeplCode() {
        return skuThreeplCode;
    }

    public void setSkuThreeplCode(String skuThreeplCode) {
        this.skuThreeplCode = skuThreeplCode;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

}
