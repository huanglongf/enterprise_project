package com.jumbo.wms.model.vmi.ckData;

import java.math.BigDecimal;
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

@Entity
@Table(name = "T_CK_INVENTORY_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CKInventoryData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6410537339752483703L;

    private Long id;

    private String dataType;

    private String skuCode;

    private String branchCode;

    private String branchDesc;

    private BigDecimal qty;

    private Date createTime;

    private Integer status;

    private String batchNum;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CK_INVENTORY_DATA", sequenceName = "S_T_CK_INVENTORY_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CK_INVENTORY_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DATA_TYPE", length = 10)
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "BRANCH_CODE", length = 10)
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Column(name = "BRANCH_DESC", length = 50)
    public String getBranchDesc() {
        return branchDesc;
    }

    public void setBranchDesc(String branchDesc) {
        this.branchDesc = branchDesc;
    }

    @Column(name = "QTY")
    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "BATCH_NUM")
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }


}
