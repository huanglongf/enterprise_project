package com.jumbo.wms.model.vmi.GucciData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库反馈给HUB实体
 * 
 */
public class GucciVMIInFB extends BaseModel {

    private static final long serialVersionUID = -7042617713586612398L;

    /**
     * 相关作业单（多个作业单号用；拼接）
     */
    private String staCode;

    /**
     * 文档类型
     */
    private String JDADocumentType;

    /**
     * 供应商编号
     */
    private String vendorNumber;

    /**
     * 入库指令
     */
    private String JDADocumentNumber;

    /**
     * JDA入库时间
     */
    private Date JDADocumentDate;

    /**
     * 宝尊收货-上架日期
     */
    private Date receiptDate;

    /**
     * 物理收货时间
     */
    private Date physicalRecDate;

    /**
     * JDA系统的仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 反馈明细行
     */
    private List<GucciVMIInFBLine> lines;

    public String getJDADocumentType() {
        return JDADocumentType;
    }

    public void setJDADocumentType(String jDADocumentType) {
        JDADocumentType = jDADocumentType;
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

    public Date getJDADocumentDate() {
        return JDADocumentDate;
    }

    public void setJDADocumentDate(Date jDADocumentDate) {
        JDADocumentDate = jDADocumentDate;
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


    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Date getPhysicalRecDate() {
        return physicalRecDate;
    }

    public void setPhysicalRecDate(Date physicalRecDate) {
        this.physicalRecDate = physicalRecDate;
    }

    public List<GucciVMIInFBLine> getLines() {
        if (this.lines == null) {
            this.lines = new ArrayList<GucciVMIInFBLine>();
        }
        return lines;
    }

    public void setLines(List<GucciVMIInFBLine> lines) {
        this.lines = lines;
    }
}
