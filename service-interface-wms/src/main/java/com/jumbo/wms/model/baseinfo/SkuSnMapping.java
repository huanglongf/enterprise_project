package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 商品SN验证
 * 
 */
@Entity
@Table(name = "T_WH_SKU_SN_MAPPING")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuSnMapping extends BaseModel {

    private static final long serialVersionUID = 1559260313565961378L;

    private Long id;

    /**
     * sn
     */
    private String sn;

    /**
     * 仓库组织ID
     */
    private Long ouId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * version
     */
    private Integer version;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 作业操作单id
     */
    private Long stvId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SKU_SN_MAPPING", sequenceName = "S_T_WH_SKU_SN_MAPPING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_SKU_SN_MAPPING")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }


}
