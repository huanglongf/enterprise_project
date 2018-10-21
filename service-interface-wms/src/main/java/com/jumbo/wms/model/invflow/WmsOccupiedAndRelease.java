package com.jumbo.wms.model.invflow;

import java.io.Serializable;
import java.util.Date;

/**
 * WMS3.0通知IM的无前置单号的占用数据和取消释放数据
 *
 */
public class WmsOccupiedAndRelease implements Serializable {

    private static final long serialVersionUID = 2000165383085084349L;
    /**
     * 唯一标识
     */
    private Long id;
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
     * wms3.0作业单号staCode
     */
    private String pfDocNo;
    /**
     * wms3.0单据类型
     */
    private String docType;
    /**
     * 库存事务时间
     */
    private Date invTransactionTime;

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

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getInvStatusCode() {
        return invStatusCode;
    }

    public void setInvStatusCode(String invStatusCode) {
        this.invStatusCode = invStatusCode;
    }

    public String getPfDocNo() {
        return pfDocNo;
    }

    public void setPfDocNo(String pfDocNo) {
        this.pfDocNo = pfDocNo;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Date getInvTransactionTime() {
        return invTransactionTime;
    }

    public void setInvTransactionTime(Date invTransactionTime) {
        this.invTransactionTime = invTransactionTime;
    }

}
