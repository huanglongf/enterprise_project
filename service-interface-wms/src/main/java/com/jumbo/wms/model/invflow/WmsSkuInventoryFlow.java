package com.jumbo.wms.model.invflow;

import java.io.Serializable;
import java.util.Date;

/**
 * WMS3.0通知上位系统库存流水信息
 *
 */
public class WmsSkuInventoryFlow implements Serializable {
    private static final long serialVersionUID = -3116578352943865662L;
    /**
     * 数据唯一标识
     */
    private Long id;
    /**
     * 商品唯一编码
     */
    private String upc;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 店铺编码
     */
    private String storeCode;
    /**
     * 电商平台订单号
     */
    private String ecOrderCode;
    /**
     * WMS单据类型
     */
    private String odoType;
    /**
     * 调整数量 支持正负
     */
    private Long revisionQty;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 库存类型
     */
    private String invType;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 生产日期
     */
    private Date mfgDate;
    /**
     * 失效日期
     */
    private Date expDate;
    /**
     * 原产地
     */
    private String countryOfOrigin;
    /**
     * 库存属性1
     */
    private String invAttr1;
    /**
     * 库存属性2
     */
    private String invAttr2;
    /**
     * 库存属性3
     */
    private String invAttr3;
    /**
     * 库存属性4
     */
    private String invAttr4;
    /**
     * 库存属性5
     */
    private String invAttr5;
    /**
     * 库存事务类型
     */
    private String invTransactionType;
    /**
     * 仓库编码
     */
    private String whCode;
    /**
     * 库存事务时间
     */
    private Date invTransactionTime;
    /**
     * 创建时间
     */
    private Date createTime;

    private String staCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getEcOrderCode() {
        return ecOrderCode;
    }

    public void setEcOrderCode(String ecOrderCode) {
        this.ecOrderCode = ecOrderCode;
    }

    public String getOdoType() {
        return odoType;
    }

    public void setOdoType(String odoType) {
        this.odoType = odoType;
    }

    public Long getRevisionQty() {
        return revisionQty;
    }

    public void setRevisionQty(Long revisionQty) {
        this.revisionQty = revisionQty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(Date mfgDate) {
        this.mfgDate = mfgDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getInvAttr1() {
        return invAttr1;
    }

    public void setInvAttr1(String invAttr1) {
        this.invAttr1 = invAttr1;
    }

    public String getInvAttr2() {
        return invAttr2;
    }

    public void setInvAttr2(String invAttr2) {
        this.invAttr2 = invAttr2;
    }

    public String getInvAttr3() {
        return invAttr3;
    }

    public void setInvAttr3(String invAttr3) {
        this.invAttr3 = invAttr3;
    }

    public String getInvAttr4() {
        return invAttr4;
    }

    public void setInvAttr4(String invAttr4) {
        this.invAttr4 = invAttr4;
    }

    public String getInvAttr5() {
        return invAttr5;
    }

    public void setInvAttr5(String invAttr5) {
        this.invAttr5 = invAttr5;
    }

    public String getInvTransactionType() {
        return invTransactionType;
    }

    public void setInvTransactionType(String invTransactionType) {
        this.invTransactionType = invTransactionType;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Date getInvTransactionTime() {
        return invTransactionTime;
    }

    public void setInvTransactionTime(Date invTransactionTime) {
        this.invTransactionTime = invTransactionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

}
