package com.jumbo.wms.model.warehouse;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库作业明细单
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_sku_imperfect")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuImperfect extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -2417253301938115649L;
    private Long id;
    private Date createTime;
    private String owner;
    private String defectCode;
    private String defectType;
    private String defectWhy;
    private Integer status;
    private Sku sku;
    private StockTransApplication sta;
    private Integer qty;
    private OperationUnit ouId;
    private String poId;
    private String memo;
    private String factoryCode;

    @Column(name = "po_Id")
    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "factory_Code")
    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_sku_imperfect", sequenceName = "S_t_wh_sku_imperfect", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_sku_imperfect")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "defect_Code")
    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    @Column(name = "defect_Type")
    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    @Column(name = "defect_Why")
    public String getDefectWhy() {
        return defectWhy;
    }

    public void setDefectWhy(String defectWhy) {
        this.defectWhy = defectWhy;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sta_id")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ou_id")
    public OperationUnit getOuId() {
        return ouId;
    }

    public void setOuId(OperationUnit ouId) {
        this.ouId = ouId;
    }



}
