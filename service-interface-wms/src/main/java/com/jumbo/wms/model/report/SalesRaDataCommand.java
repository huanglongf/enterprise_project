package com.jumbo.wms.model.report;

import java.math.BigDecimal;
import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class SalesRaDataCommand extends BaseModel {

    private static final long serialVersionUID = 6219488809311474137L;

    private Date approveTime;

    private String raType;

    private String raCode;

    private BigDecimal mdPrice;

    private BigDecimal actualPrice;

    private String transCode;

    private String skuName;

    private String pfTranNum;

    private String productCode;

    private String productDesc;

    private String productCate;

    private String productLine;

    private String consumerGroup;

    private String keyProperties;

    private Long quantity;

    private BigDecimal posSales;

    private BigDecimal refundAmt;

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getRaType() {
        return raType;
    }

    public void setRaType(String raType) {
        this.raType = raType;
    }

    public String getRaCode() {
        return raCode;
    }

    public void setRaCode(String raCode) {
        this.raCode = raCode;
    }

    public BigDecimal getMdPrice() {
        return mdPrice;
    }

    public void setMdPrice(BigDecimal mdPrice) {
        this.mdPrice = mdPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getPfTranNum() {
        return pfTranNum;
    }

    public void setPfTranNum(String pfTranNum) {
        this.pfTranNum = pfTranNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductCate() {
        return productCate;
    }

    public void setProductCate(String productCate) {
        this.productCate = productCate;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPosSales() {
        return posSales;
    }

    public void setPosSales(BigDecimal posSales) {
        this.posSales = posSales;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

}
