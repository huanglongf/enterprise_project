package com.jumbo.wms.model.vmi.GucciData;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库反馈HUB明细行
 * 
 */
public class GucciVMIInFBLine extends BaseModel {

    private static final long serialVersionUID = -8727039397749747289L;

    /**
     * 供应商编号
     */
    private String vendorNumber;

    /**
     * 相关入库指令
     */
    private String JDADocumentNumber;

    /**
     * 对应入库明细行号
     */
    private String rowNumberDetail;

    /**
     * 库存状态 （若为残次品：DG）
     */
    private String invStatus;

    /**
     * 下单订单号
     */
    private String JDAPoNumber;

    /**
     * 商品编码
     */
    private String SkuNumber;

    /**
     * 计划收获量
     */
    private Long qtyToBeReceived;

    /**
     * 实际收获量
     */
    private Long qtyReceived;

    /**
     * JDA仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    public String getRowNumberDetail() {
        return rowNumberDetail;
    }

    public void setRowNumberDetail(String rowNumberDetail) {
        this.rowNumberDetail = rowNumberDetail;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public Long getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(Long qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public String getJDAPoNumber() {
        return JDAPoNumber;
    }

    public void setJDAPoNumber(String jDAPoNumber) {
        JDAPoNumber = jDAPoNumber;
    }

    public String getSkuNumber() {
        return SkuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        SkuNumber = skuNumber;
    }

    public Long getQtyToBeReceived() {
        return qtyToBeReceived;
    }

    public void setQtyToBeReceived(Long qtyToBeReceived) {
        this.qtyToBeReceived = qtyToBeReceived;
    }

    public String getJDAWarehouseCode() {
        return JDAWarehouseCode;
    }

    public void setJDAWarehouseCode(String jDAWarehouseCode) {
        JDAWarehouseCode = jDAWarehouseCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public String getJDADocumentNumber() {
        return JDADocumentNumber;
    }

    public void setJDADocumentNumber(String jDADocumentNumber) {
        JDADocumentNumber = jDADocumentNumber;
    }
}
