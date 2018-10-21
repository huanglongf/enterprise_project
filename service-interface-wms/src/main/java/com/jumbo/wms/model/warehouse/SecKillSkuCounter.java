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

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 秒杀商品计数器
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SEC_KILL_SKU_COUNTER")
public class SecKillSkuCounter extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8714822634289200886L;
    /**
     * PK
     */
    private Long id;
    /**
     * skus
     */
    private String skus;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 创建时间
     */
    private Date crateTime;
    /**
     * 所属仓库
     */
    private OperationUnit ou;
    /**
     * 作业单ID
     */
    private Long staId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SEC_KILL_SKU_COUNTER", sequenceName = "S_T_WH_SEC_KILL_SKU_COUNTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SEC_KILL_SKU_COUNTER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKUS")
    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "CREATE_TIME")
    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_SECKILLSKUCOUNTER_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "STA_ID")
    @Index(name = "IDX_SECKILLSKUCOUNTER_STA")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }
}
