package com.jumbo.wms.model.automaticEquipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 *商品上架类型
 * @createDate 2016年1月19日 下午7:21:46
 */
@Entity
@Table(name = "T_BI_INV_SKU_TYPE")
public class SkuType extends BaseModel {
    private static final long serialVersionUID = -4694629786638428597L;

    private Long id;
    private String name;

    /**
     * 状态(0可用1禁用)
     */
    private Boolean status = false;

    @Id
    @SequenceGenerator(name = "S_T_BI_INV_SKU_TYPE_ID_GENERATOR", sequenceName = "S_T_BI_INV_SKU_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_BI_INV_SKU_TYPE_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", length = 1)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }




}
