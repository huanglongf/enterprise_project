package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 仓库库存
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_ST_LOG_QUEUE")
public class LogQueue extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -4400264964994197453L;
    private Long id;
    private Long whouId;
    private String whouCode;
    private String transTypeName;
    private Date createTime;
    private Long skuId;
    private String customerSkuCode;
    private String customerId;
    private String customerCode;
    private Long salesAvailQty;
    private String channelCode;
    private Long status;
    private String batchId;
    private Integer errorCount;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANS_TYPE_NAME")
    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "CUSTOMER_SKU_CODE")
    public String getCustomerSkuCode() {
        return customerSkuCode;
    }

    public void setCustomerSkuCode(String customerSkuCode) {
        this.customerSkuCode = customerSkuCode;
    }

    @Column(name = "CUSTOMER_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Column(name = "CUSTOMER_CODE")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "SALES_AVAIL_QTY")
    public Long getSalesAvailQty() {
        return salesAvailQty;
    }

    public void setSalesAvailQty(Long salesAvailQty) {
        this.salesAvailQty = salesAvailQty;
    }

    @Column(name = "CHANNEL_CODE")
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Column(name = "status")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "batch_ID")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Column(name = "wh_ou_id")
    public Long getWhouId() {
        return whouId;
    }

    public void setWhouId(Long whouId) {
        this.whouId = whouId;
    }

    @Column(name = "wh_ou_code")
    public String getWhouCode() {
        return whouCode;
    }

    public void setWhouCode(String whouCode) {
        this.whouCode = whouCode;
    }

    @Column(name = "error_count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
