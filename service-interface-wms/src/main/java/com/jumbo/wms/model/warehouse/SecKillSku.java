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
 * 秒杀商品
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SEC_KILL_SKU")
public class SecKillSku extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3077629681945671512L;
    /**
     * PK
     */
    private Long id;
    /**
     * 过期时间
     */
    private Date expirationTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * SKUS
     */
    private String skus;
    /**
     * 是否系统秒杀
     */
    private Boolean isSystem;
    /**
     * 所属仓库
     */
    private OperationUnit ou;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SEC_KILL_SKU", sequenceName = "S_T_WH_SEC_KILL_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SEC_KILL_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EXPIRATION_TIME")
    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SKUS")
    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    @Column(name = "IS_SYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_SECKILLSKU_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

}
