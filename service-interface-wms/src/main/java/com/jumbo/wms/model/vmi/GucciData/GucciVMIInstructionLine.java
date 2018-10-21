package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库指令明细行
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_INSTRUCTION_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIInstructionLine extends BaseModel {

    private static final long serialVersionUID = -7710614648628071587L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 相关入库指令
     */
    private GucciVMIInstruction gucciVMIInstruction;

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

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_IN_LINE", sequenceName = "S_T_GUCCI_VMI_IN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_IN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUCCI_VMI_IN_ID")
    @Index(name = "IDX_GUCCI_VMI_INSTRUCTION_ID")
    public GucciVMIInstruction getGucciVMIInstruction() {
        return gucciVMIInstruction;
    }

    public void setGucciVMIInstruction(GucciVMIInstruction gucciVMIInstruction) {
        this.gucciVMIInstruction = gucciVMIInstruction;
    }

    @Column(name = "ROW_NUMBER_DETAIL")
    public String getRowNumberDetail() {
        return rowNumberDetail;
    }

    public void setRowNumberDetail(String rowNumberDetail) {
        this.rowNumberDetail = rowNumberDetail;
    }

    @Column(name = "ITEM_PRODUCTION_DATE")
    public Date getItemProductionDate() {
        return itemProductionDate;
    }

    public void setItemProductionDate(Date itemProductionDate) {
        this.itemProductionDate = itemProductionDate;
    }

    @Column(name = "DROP_SHIPMENT_FLAG")
    public String getDropShipmentFlag() {
        return dropShipmentFlag;
    }

    public void setDropShipmentFlag(String dropShipmentFlag) {
        this.dropShipmentFlag = dropShipmentFlag;
    }

    @Column(name = "JDA_PO_NUMBER")
    public String getJDAPoNumber() {
        return JDAPoNumber;
    }

    public void setJDAPoNumber(String jDAPoNumber) {
        JDAPoNumber = jDAPoNumber;
    }

    @Column(name = "SPEDITION_ID")
    public String getSpeditionId() {
        return speditionId;
    }

    public void setSpeditionId(String speditionId) {
        this.speditionId = speditionId;
    }

    @Column(name = "LGI_CARTON_NUMBER")
    public String getLGICartonNumber() {
        return LGICartonNumber;
    }

    public void setLGICartonNumber(String lGICartonNumber) {
        LGICartonNumber = lGICartonNumber;
    }

    @Column(name = "SKU_NUMBER")
    public String getSkuNumber() {
        return SkuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        SkuNumber = skuNumber;
    }

    @Column(name = "QTY_TO_BE_RECEIVED")
    public Long getQtyToBeReceived() {
        return qtyToBeReceived;
    }

    public void setQtyToBeReceived(Long qtyToBeReceived) {
        this.qtyToBeReceived = qtyToBeReceived;
    }

    @Column(name = "STORE_OF_DESTINATION")
    public String getStoreOfDestination() {
        return storeOfDestination;
    }

    public void setStoreOfDestination(String storeOfDestination) {
        this.storeOfDestination = storeOfDestination;
    }

    @Column(name = "COUNTRY_OF_ORIGIN")
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Column(name = "JDA_WAREHOUSE_CODE")
    public String getJDAWarehouseCode() {
        return JDAWarehouseCode;
    }

    public void setJDAWarehouseCode(String jDAWarehouseCode) {
        JDAWarehouseCode = jDAWarehouseCode;
    }

    @Column(name = "BRAND_CODE")
    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @Column(name = "VENDOR_NUMBER")
    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    @Column(name = "JDA_DOCUMENT_NUMBER")
    public String getJDADocumentNumber() {
        return JDADocumentNumber;
    }

    public void setJDADocumentNumber(String jDADocumentNumber) {
        JDADocumentNumber = jDADocumentNumber;
    }

}
