package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 套装组合商品日志
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_PACKAGE_SKU_LOG")
public class PackageSkuLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -126482465021416320L;
    /**
     * id
     */
    private Long id;
    /**
     * 作业单商品总数量
     */
    private Long staTotalSkuQty;
    /**
     * 作业单商品种类数
     */
    private Long skuQty;
    /**
     * 过期时间
     */
    private Date expTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否系统创建
     */
    private Boolean isSystem;
    /**
     * skus1
     */
    private String skus1;
    /**
     * skus2
     */
    private String skus2;
    /**
     * skus3
     */
    private String skus3;
    /**
     * 删除时间/日志记录时间
     */
    private Date logTime;
    /**
     * 仓库组织节点id
     */
    private OperationUnit ou;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_TOTAL_SKU_QTY")
    public Long getStaTotalSkuQty() {
        return staTotalSkuQty;
    }

    public void setStaTotalSkuQty(Long staTotalSkuQty) {
        this.staTotalSkuQty = staTotalSkuQty;
    }

    @Column(name = "SKU_QTY")
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "EXP_TIME")
    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ISSYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Column(name = "SKUS1")
    public String getSkus1() {
        return skus1;
    }

    public void setSkus1(String skus1) {
        this.skus1 = skus1;
    }

    @Column(name = "SKUS2")
    public String getSkus2() {
        return skus2;
    }

    public void setSkus2(String skus2) {
        this.skus2 = skus2;
    }

    @Column(name = "SKUS3")
    public String getSkus3() {
        return skus3;
    }

    public void setSkus3(String skus3) {
        this.skus3 = skus3;
    }

    @Column(name = "LOG_TIME")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_PACKAGESKULOG_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }
}
