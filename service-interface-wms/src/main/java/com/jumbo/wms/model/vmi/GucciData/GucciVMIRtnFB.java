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
 * Gucci 退大仓反馈头
 * 
 */
@Entity
@Table(name = "T_GUCCI_VMI_RTN_FB")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciVMIRtnFB extends BaseModel {

    private static final long serialVersionUID = -4582567515192871330L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 作业单号
     */
    private String staCode;

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
     * 仓库拣货完成时间
     */
    private Date goodsIssueDate;

    /**
     * JDA系统的仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态 1已发送 0新建
     */
    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_VMI_RTN_FB", sequenceName = "S_T_GUCCI_VMI_RTN_FB", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_VMI_RTN_FB")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "GOODS_ISSUE_DATE")
    public Date getGoodsIssueDate() {
        return goodsIssueDate;
    }

    public void setGoodsIssueDate(Date goodsIssueDate) {
        this.goodsIssueDate = goodsIssueDate;
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

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

}
