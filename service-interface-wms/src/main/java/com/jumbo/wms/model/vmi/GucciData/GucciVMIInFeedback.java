package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 普通入库反馈/退货入库反馈
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_IN_FEEDBACK")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIInFeedback extends BaseModel {

    private static final long serialVersionUID = -5491678412485446828L;

    /**
     * 主键id
     */
    private Long id;

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
     * 推送状态 0:新建 ，1:已发送
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

    /**
     * 单据类型 0:普通入库反馈 1：退货入库反馈
     */
    private Integer type;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_IN_FEEDBACK", sequenceName = "S_T_GUCCI_VMI_IN_FEEDBACK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_IN_FEEDBACK")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "JDA_DOCUMENT_TYPE")
    public String getJDADocumentType() {
        return JDADocumentType;
    }

    public void setJDADocumentType(String jDADocumentType) {
        JDADocumentType = jDADocumentType;
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

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "ERR_COUNT")
    public Integer getErrCount() {
        return errCount;
    }

    public void setErrCount(Integer errCount) {
        this.errCount = errCount;
    }

    @Column(name = "RECEIPT_DATE")
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Column(name = "PHYSICAL_REC_DATE")
    public Date getPhysicalRecDate() {
        return physicalRecDate;
    }

    public void setPhysicalRecDate(Date physicalRecDate) {
        this.physicalRecDate = physicalRecDate;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
