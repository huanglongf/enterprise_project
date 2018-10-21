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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 特殊处理商品
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SPECIAL_SKU")
public class SpecialSku extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * PK id
     */
    private Long id;
    /**
     * 组织节点ID
     */
    private OperationUnit ou;
    /**
     * 商品ID
     */
    private Sku sku;
    /**
     * 特殊处理类型
     */
    private SpecialSkuType specialType;
    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SPECIAL_SKU", sequenceName = "S_T_WH_SPECIAL_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SPECIAL_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_SPECIAL_SKU_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_SPECIAL_SKU_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "SPECIAL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.SpecialSkuType")})
    public SpecialSkuType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialSkuType specialType) {
        this.specialType = specialType;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
