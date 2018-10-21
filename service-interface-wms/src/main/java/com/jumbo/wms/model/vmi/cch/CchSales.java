package com.jumbo.wms.model.vmi.cch;

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
@Table(name = "T_SO_SO_CACHE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CchSales extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5358662632914299476L;

    private Long id;

    private String soCode;

    private String shopCode;

    private String cchCode;

    private Date transactionDate;

    private String barCode;

    private BigDecimal saleQuantity;

    private BigDecimal returnQuantity;

    private BigDecimal amount;

    private BigDecimal discount;

    private Integer type;// 销售=1，退换=3

    private Long shopId;

    private Long skuId;

    private String outOrderCode;

    private BigDecimal listPrice;

    private String alipyMemberId;

    private String customerName;

    private String mobile;

    private String address;

    private String zipCode;

    private Date version;

    private Integer syncStatus;

    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SO_SO_CACHE", sequenceName = "S_T_SO_SO_CACHE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SO_SO_CACHE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SO_CODE", length = 20)
    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    @Column(name = "SHOP_CODE", length = 20)
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "CCH_CODE", length = 20)
    public String getCchCode() {
        return cchCode;
    }

    public void setCchCode(String cchCode) {
        this.cchCode = cchCode;
    }

    @Column(name = "TRANSACTION_DATE")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Column(name = "BAR_CODE", length = 20)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "sale_Quantity")
    public BigDecimal getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(BigDecimal saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    @Column(name = "return_Quantity")
    public BigDecimal getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "shop_Id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "sku_Id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "out_Order_Code", length = 50)
    public String getOutOrderCode() {
        return outOrderCode;
    }

    public void setOutOrderCode(String outOrderCode) {
        this.outOrderCode = outOrderCode;
    }

    @Column(name = "list_Price")
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "alipy_Member_Id", length = 30)
    public String getAlipyMemberId() {
        return alipyMemberId;
    }

    public void setAlipyMemberId(String alipyMemberId) {
        this.alipyMemberId = alipyMemberId;
    }

    @Column(name = "customer_Name", length = 20)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "mobile", length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "address", length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "zip_Code", length = 10)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "version")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "sync_Status")
    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Column(name = "batch_Num", length = 10)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }


}
