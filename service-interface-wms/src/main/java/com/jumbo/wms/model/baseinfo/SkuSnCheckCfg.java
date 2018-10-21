package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 商品SN校验规则
 * 
 */
@Entity
@Table(name = "T_BI_INV_SKU_SN_CHECK_CFG")
public class SkuSnCheckCfg extends BaseModel {

    private static final long serialVersionUID = -3670420560669439269L;

    private Long id;

    /**
     * 类型
     */
    private SkuSnCheckCfgType type;

    /**
     * 内容
     */
    private String memo;


    private SkuSnCheckMode snCheckMode;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_INV_SKU_SN_CHECK_CFG", sequenceName = "S_T_BI_INV_SKU_SN_CHECK_CFG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INV_SKU_SN_CHECK_CFG")
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCheckCfgType")})
    public SkuSnCheckCfgType getType() {
        return type;
    }


    public void setType(SkuSnCheckCfgType type) {
        this.type = type;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "SN_CHECK_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCheckMode")})
    public SkuSnCheckMode getSnCheckMode() {
        return snCheckMode;
    }


    public void setSnCheckMode(SkuSnCheckMode snCheckMode) {
        this.snCheckMode = snCheckMode;
    }



}
