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
 * Gucci 入库反馈明细行
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_IN_FB_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIInFeedbackLine extends BaseModel {

    private static final long serialVersionUID = -6718731733002506768L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 相关入库反馈头信息
     */
    private GucciVMIInFeedback gucciVMIInFeedback;

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
     * 库存状态 （良品:GG[待确认];残次品：DG）
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

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_IN_FB_LINE", sequenceName = "S_T_GUCCI_VMI_IN_FB_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_IN_FB_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ROW_NUMBER_DETAIL")
    public String getRowNumberDetail() {
        return rowNumberDetail;
    }

    public void setRowNumberDetail(String rowNumberDetail) {
        this.rowNumberDetail = rowNumberDetail;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUCCI_VMI_IN_FD_ID")
    public GucciVMIInFeedback getGucciVMIInFeedback() {
        return gucciVMIInFeedback;
    }

    public void setGucciVMIInFeedback(GucciVMIInFeedback gucciVMIInFeedback) {
        this.gucciVMIInFeedback = gucciVMIInFeedback;
    }

    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "QTY_RECEIVED")
    public Long getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(Long qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    @Column(name = "JDA_PO_NUMBER")
    public String getJDAPoNumber() {
        return JDAPoNumber;
    }

    public void setJDAPoNumber(String jDAPoNumber) {
        JDAPoNumber = jDAPoNumber;
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
