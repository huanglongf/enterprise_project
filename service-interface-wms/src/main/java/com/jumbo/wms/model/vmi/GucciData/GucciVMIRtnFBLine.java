package com.jumbo.wms.model.vmi.GucciData;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 退大仓反馈明细行
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_RTN_FB_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIRtnFBLine extends BaseModel {

    private static final long serialVersionUID = -3110621908145469532L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 退大仓反馈头
     */
    private GucciVMIRtnFB gucciVMIRtnFBId;

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


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_RTN_LINE", sequenceName = "S_T_GUCCI_VMI_RTN_FB_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_RTN_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUCCI_VMI_RTN_FB_ID")
    public GucciVMIRtnFB getGucciVMIRtnFBId() {
        return gucciVMIRtnFBId;
    }

    public void setGucciVMIRtnFBId(GucciVMIRtnFB gucciVMIRtnFBId) {
        this.gucciVMIRtnFBId = gucciVMIRtnFBId;
    }

    @Column(name = "JDA_BATCH_NUMBER")
    public String getJDABatchNumber() {
        return JDABatchNumber;
    }

    public void setJDABatchNumber(String jDABatchNumber) {
        JDABatchNumber = jDABatchNumber;
    }

    @Column(name = "PD_NUMBER")
    public String getPDNumber() {
        return PDNumber;
    }

    public void setPDNumber(String pDNumber) {
        PDNumber = pDNumber;
    }

    @Column(name = "TO_JDA_LOCATION")
    public String getToJDALocation() {
        return toJDALocation;
    }

    public void setToJDALocation(String toJDALocation) {
        this.toJDALocation = toJDALocation;
    }

    @Column(name = "DOCUMENT_TYPE")
    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Column(name = "PCK_NUMBER")
    public String getPckNumber() {
        return pckNumber;
    }

    public void setPckNumber(String pckNumber) {
        this.pckNumber = pckNumber;
    }

    @Column(name = "PICKING_LIST_NO")
    public String getPickingListNo() {
        return pickingListNo;
    }

    public void setPickingListNo(String pickingListNo) {
        this.pickingListNo = pickingListNo;
    }

    @Column(name = "CARTON_NUMBER")
    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    @Column(name = "SKU_NUMBER")
    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    @Column(name = "REQUESTED_QUANTITY")
    public Long getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Long requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    @Column(name = "SHIPPED_QUANTITY")
    public Long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
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
}
