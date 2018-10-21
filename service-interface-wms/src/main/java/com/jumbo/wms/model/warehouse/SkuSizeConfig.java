package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jumbo.wms.model.BaseModel;

/**
 * 商品大小件分类配置
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SKU_SIZE_CONFIG",uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class SkuSizeConfig extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 6585120186149088210L;
    /**
     * PK
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 最小尺寸
     */
    private BigDecimal minSize;
    /**
     * 最大尺寸
     */
    private BigDecimal maxSize;
    /**
     * 团购商品数量限制
     */
    private Long groupSkuQtyLimit;
    /**
     * 是否默认选择
     */
    private Boolean isDefault;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU_SIZE_CONFIG", sequenceName = "S_T_WH_SKU_SIZE_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU_SIZE_CONFIG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MIN_SIZE")
    public BigDecimal getMinSize() {
        return minSize;
    }

    public void setMinSize(BigDecimal minSize) {
        this.minSize = minSize;
    }

    @Column(name = "MAX_SIZE")
    public BigDecimal getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(BigDecimal maxSize) {
        this.maxSize = maxSize;
    }

    @Column(name = "GROUP_SKU_QTY_LIMIT")
    public Long getGroupSkuQtyLimit() {
        return groupSkuQtyLimit;
    }

    public void setGroupSkuQtyLimit(Long groupSkuQtyLimit) {
        this.groupSkuQtyLimit = groupSkuQtyLimit;
    }
    @Column(name = "IS_DEFAULT")
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
