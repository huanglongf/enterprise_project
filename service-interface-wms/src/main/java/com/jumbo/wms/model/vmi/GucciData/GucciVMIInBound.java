package com.jumbo.wms.model.vmi.GucciData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库指令（接口数据）
 * 
 */
public class GucciVMIInBound extends BaseModel {

    private static final long serialVersionUID = 6873630545605857736L;

    /**
     * 供应商编号
     */
    private String vendorNumber;

    /**
     * 入库指令
     */
    private String JDADocumentNumber;

    /**
     * 原始ASNNumber
     */
    private String originalASNNumber;

    /**
     * ICTNumber
     */
    private String ICTNumber;

    /**
     * 入库时间
     */
    private Date JDADocumentDate;

    /**
     * JDA系统的仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 始发国家
     */
    private String countryOfOrigin;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 入库指令明细
     */
    private List<GucciVMIInBoundLine> gucciVMIInBoundLines;

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

    public String getOriginalASNNumber() {
        return originalASNNumber;
    }

    public void setOriginalASNNumber(String originalASNNumber) {
        this.originalASNNumber = originalASNNumber;
    }

    public String getICTNumber() {
        return ICTNumber;
    }

    public void setICTNumber(String iCTNumber) {
        ICTNumber = iCTNumber;
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<GucciVMIInBoundLine> getGucciVMIInBoundLines() {
        if (this.gucciVMIInBoundLines == null) {
            this.gucciVMIInBoundLines = new ArrayList<GucciVMIInBoundLine>();
        }
        return gucciVMIInBoundLines;
    }

    public void setGucciVMIInBoundLines(List<GucciVMIInBoundLine> gucciVMIInBoundLines) {
        this.gucciVMIInBoundLines = gucciVMIInBoundLines;
    }

}
