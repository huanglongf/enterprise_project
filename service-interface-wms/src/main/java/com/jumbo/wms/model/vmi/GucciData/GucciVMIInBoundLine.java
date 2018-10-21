package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库指令明细行（接口数据）
 * 
 */
public class GucciVMIInBoundLine extends BaseModel {

    private static final long serialVersionUID = -3253196673213005153L;

    /**
     * 供应商编号
     */
    private String vendorNumber;

    /**
     * 入库指令
     */
    private String JDADocumentNumber;

    /**
     * 入库明细行号
     */
    private String rowNumberDetail;

    /**
     * 商品生产日期
     */
    private Date itemProductionDate;

    /**
     * 装载标识
     */
    private String dropShipmentFlag;

    /**
     * 下单订单号
     */
    private String JDAPoNumber;

    /**
     * LGI跟踪号码
     */
    private String speditionId;

    /**
     * LGI箱号
     */
    private String LGICartonNumber;

    /**
     * 商品编码
     */
    private String SkuNumber;

    /**
     * 计划收获量
     */
    private Long qtyToBeReceived;

    /**
     * 目的地仓库
     */
    private String storeOfDestination;

    /**
     * 始发国家
     */
    private String countryOfOrigin;

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

    public Date getItemProductionDate() {
        return itemProductionDate;
    }

    public void setItemProductionDate(Date itemProductionDate) {
        this.itemProductionDate = itemProductionDate;
    }

    public String getDropShipmentFlag() {
        return dropShipmentFlag;
    }

    public void setDropShipmentFlag(String dropShipmentFlag) {
        this.dropShipmentFlag = dropShipmentFlag;
    }

    public String getJDAPoNumber() {
        return JDAPoNumber;
    }

    public void setJDAPoNumber(String jDAPoNumber) {
        JDAPoNumber = jDAPoNumber;
    }

    public String getSpeditionId() {
        return speditionId;
    }

    public void setSpeditionId(String speditionId) {
        this.speditionId = speditionId;
    }

    public String getLGICartonNumber() {
        return LGICartonNumber;
    }

    public void setLGICartonNumber(String lGICartonNumber) {
        LGICartonNumber = lGICartonNumber;
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

    public String getStoreOfDestination() {
        return storeOfDestination;
    }

    public void setStoreOfDestination(String storeOfDestination) {
        this.storeOfDestination = storeOfDestination;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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
