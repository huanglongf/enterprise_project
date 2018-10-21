package com.jumbo.wms.model.invflow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * WMS3.0通知IM的无前置单号的占用数据和取消释放数据归档表
 *
 */
@Entity
@Table(name = "T_WH_IM_OCCUPIED_RELEASE_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WmsIMOccupiedAndReleaseLog extends BaseModel {

    private static final long serialVersionUID = 6871780980322733340L;
    /**
     * 主键
     */
    private Long Id;
    /**
     * 运营主体（客户编码）
     */
    private String customerCode;
    /**
     * 库存主体代码(店铺编码)
     */
    private String ownerCode;
    /**
     * 存货点代码（仓库编码）
     */
    private String binCode;
    /**
     * SKU编码
     */
    private String skuCode;
    /**
     * 执行数量 有正负（创建为负数,取消为正）
     */
    private Long qty;
    /**
     * 库存状态代码 10：良品 20：残次品 30：报废品 40：待处理品
     */
    private String invStatusCode;
    /**
     * 作业单号staCode
     */
    private String staCode;
    /**
     * 单据类型
     */
    private String staType;
    /**
     * 库存事务时间
     */
    private Date invTransactionTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 数据类型，0：占用；1：取消释放
     */
    private Integer type;
    /**
     * 推送状态，0：新建；1已推送
     */
    private Integer status;
    /**
     * 日志时间
     */
    private Date logTime;
    /**
     * 唯一键
     */
    private Long uniquekey;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "UNIQUE_KEY")
    public Long getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(Long uniquekey) {
        this.uniquekey = uniquekey;
    }

    @Column(name = "CUSTOMER_CODE")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "OWNER_CODE")
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Column(name = "BIN_CODE")
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "INV_STATUS_CODE")
    public String getInvStatusCode() {
        return invStatusCode;
    }

    public void setInvStatusCode(String invStatusCode) {
        this.invStatusCode = invStatusCode;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STA_TYPE")
    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

    @Column(name = "INV_TRANSACTION_TIME")
    public Date getInvTransactionTime() {
        return invTransactionTime;
    }

    public void setInvTransactionTime(Date invTransactionTime) {
        this.invTransactionTime = invTransactionTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "LOG_TIME")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

}
