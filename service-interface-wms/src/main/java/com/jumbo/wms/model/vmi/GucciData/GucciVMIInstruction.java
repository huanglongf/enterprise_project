package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 入库指令
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_INSTRUCTION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIInstruction extends BaseModel {

    private static final long serialVersionUID = -5206901428689095973L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 相关作业单
     */
    private Long staId;

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
     * 入库单状态 0:新建 ，1:完成， -1: 异常
     */
    private Integer status;

    /**
     * 错误次数
     */
    private Integer errCount;

    /**
     * 错误信息
     */
    private String errMsg;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_INSTRUCTION", sequenceName = "S_T_GUCCI_VMI_INSTRUCTION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_INSTRUCTION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VENDOR_NUMBER")
    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    @Column(name = "JDA_DOCUMENT_NUMBER")
    @Index(name = "INDEX_JDA_DOCUMENT_NUMBER")
    public String getJDADocumentNumber() {
        return JDADocumentNumber;
    }

    public void setJDADocumentNumber(String jDADocumentNumber) {
        JDADocumentNumber = jDADocumentNumber;
    }

    @Column(name = "ORIGINAL_ASN_NUMBER")
    public String getOriginalASNNumber() {
        return originalASNNumber;
    }

    public void setOriginalASNNumber(String originalASNNumber) {
        this.originalASNNumber = originalASNNumber;
    }

    @Column(name = "ICT_NUMBER")
    public String getICTNumber() {
        return ICTNumber;
    }

    public void setICTNumber(String iCTNumber) {
        ICTNumber = iCTNumber;
    }

    @Column(name = "JDA_DOCUMENT_DATE")
    public Date getJDADocumentDate() {
        return JDADocumentDate;
    }

    public void setJDADocumentDate(Date jDADocumentDate) {
        JDADocumentDate = jDADocumentDate;
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

    @Column(name = "COUNTRY_OF_ORIGIN")
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "ERR_MSG")
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "ERR_COUNT")
    public Integer getErrCount() {
        return errCount;
    }

    public void setErrCount(Integer errCount) {
        this.errCount = errCount;
    }
}
