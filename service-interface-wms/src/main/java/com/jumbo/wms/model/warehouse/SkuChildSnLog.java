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
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;

/**
 * sku 子sn号日志
 */
@Entity
@Table(name = "t_wh_sku_child_sn_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuChildSnLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2810498148191019009L;
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
     * SN状态
     */
    private SkuSnCardStatus status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 作业单Id
     */
    private StockTransApplication staId;
    /**
     * 作业单号
     */
    private String staCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_child_sn_log", sequenceName = "S_t_wh_sku_child_sn_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_child_sn_log")
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

    @Column(name = "BLOCK_STATUS", columnDefinition = "integer")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_LINE_STA")
    public StockTransApplication getStaId() {
        return staId;
    }

    public void setStaId(StockTransApplication staId) {
        this.staId = staId;
    }

    @Column(name = "sta_code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }


}
