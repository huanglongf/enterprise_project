package com.jumbo.wms.model.vmi.GucciData;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 退大仓反馈明细（接口数据）
 * 
 */
public class GucciVMIRtnFeedbackLine extends BaseModel {

    private static final long serialVersionUID = -3110621908145469532L;

    /**
     * JDA退仓批次号
     */
    private String JDABatchNumber;

    /**
     * PD-Number
     */
    private String PDNumber;

    /**
     * 转移地址
     */
    private String toJDALocation;

    /**
     * 文档类型
     */
    private String documentType;

    /**
     * 仓库拣货清单号所到目的地
     */
    private String pckNumber;

    /**
     * 配货单号（仅做序列单号）
     */
    private String pickingListNo;

    /**
     * 箱号
     */
    private String cartonNumber;

    /**
     * 商品upc
     */
    private String skuNumber;

    /**
     * 需求数量
     */
    private Long requestedQuantity;

    /**
     * 转移数量
     */
    private Long shippedQuantity;

    /**
     * JDA系统的仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    public String getJDABatchNumber() {
        return JDABatchNumber;
    }

    public void setJDABatchNumber(String jDABatchNumber) {
        JDABatchNumber = jDABatchNumber;
    }

    public String getPDNumber() {
        return PDNumber;
    }

    public void setPDNumber(String pDNumber) {
        PDNumber = pDNumber;
    }

    public String getToJDALocation() {
        return toJDALocation;
    }

    public void setToJDALocation(String toJDALocation) {
        this.toJDALocation = toJDALocation;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getPckNumber() {
        return pckNumber;
    }

    public void setPckNumber(String pckNumber) {
        this.pckNumber = pckNumber;
    }

    public String getPickingListNo() {
        return pickingListNo;
    }

    public void setPickingListNo(String pickingListNo) {
        this.pickingListNo = pickingListNo;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public Long getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Long requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
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
}
