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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;

/**
 * sku sn号
 */
@Entity
@Table(name = "t_wh_sku_child_sn", uniqueConstraints = {@UniqueConstraint(columnNames = {"seed_sn"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuChildSn extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4841985355728659795L;
    /**
     * PKID
     */
    private Long id;
    /**
     * 子SN号
     */
    private String seedSn;
    /**
     * sn号
     */
    private String sn;
    /**
     * 商品ID
     */
    private Sku skuId;
    /**
     * 状态
     */
    private SkuSnCardStatus status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_child_sn", sequenceName = "S_t_wh_sku_child_sn", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_child_sn")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "seed_sn")
    public String getSeedSn() {
        return seedSn;
    }

    public void setSeedSn(String seedSn) {
        this.seedSn = seedSn;
    }

    @Column(name = "sn")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_SKU_SN_SKU_ID")
    public Sku getSkuId() {
        return skuId;
    }

    public void setSkuId(Sku skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCardStatus")})
    public SkuSnCardStatus getStatus() {
        return status;
    }

    public void setStatus(SkuSnCardStatus status) {
        this.status = status;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "sys_update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
