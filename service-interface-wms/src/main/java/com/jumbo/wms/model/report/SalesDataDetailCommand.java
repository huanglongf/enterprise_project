package com.jumbo.wms.model.report;

import java.math.BigDecimal;
import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class SalesDataDetailCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5283928776479676547L;

    private Long id;

    /**
     * 发货时间
     */
    private Date transDate;

    /**
     * 付款时间
     */

    private Date payTime;

    /**
     * 订单号
     */
    private String transCode;

    /**
     * 淘宝交易编号
     */
    private String pfTransNum;

    /**
     * 款号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * size
     */
    private String size;

    /**
     * inseam
     */
    private String inseam;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 促销码
     */
    private String promotionCode;

    /**
     * 销售数量
     */
    private Integer quantity;

    /**
     * MD价格
     */
    private BigDecimal mdPrice;

    /**
     * 实际成交价
     */
    private BigDecimal actualPrice;

    /**
     * 折扣金额
     */
    private BigDecimal discount;

    /**
     * 实际销售额（扣除积分返点）
     */
    private BigDecimal actualAmt;

    /**
     * 运费
     */
    private BigDecimal transferFee;

    /**
     * 货品描述
     */
    private String productDesc;

    /**
     * 大分类
     */
    private String productCate;

    /**
     * 小分类
     */
    private String productLine;
    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 性别组
     */
    private String consumerGroup;

    private BigDecimal posPrice;

    private BigDecimal posAmt;

    private BigDecimal discountFee;

    private Long shopId;

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(BigDecimal posPrice) {
        this.posPrice = posPrice;
    }

    public BigDecimal getPosAmt() {
        return posAmt;
    }

    public void setPosAmt(BigDecimal posAmt) {
        this.posAmt = posAmt;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getLinePromotionCode() {
        return linePromotionCode;
    }

    public void setLinePromotionCode(String linePromotionCode) {
        this.linePromotionCode = linePromotionCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 关键属性值
     * 
     * @return
     */
    private String keyProperties;

    /**
     * 行促销码
     * 
     * @return
     */
    private String linePromotionCode;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getPfTransNum() {
        return pfTransNum;
    }

    public void setPfTransNum(String pfTransNum) {
        this.pfTransNum = pfTransNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getInseam() {
        return inseam;
    }

    public void setInseam(String inseam) {
        this.inseam = inseam;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(BigDecimal actualAmt) {
        this.actualAmt = actualAmt;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

}
