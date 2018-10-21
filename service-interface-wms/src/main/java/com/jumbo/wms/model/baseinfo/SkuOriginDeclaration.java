package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name = "T_WH_SKU_ORIGIN_DECLARATION")
public class SkuOriginDeclaration extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2859587778254172869L;

    /**
     * PK
     */
    private Long id;
    /**
     * 商品关联id
     */
    private Long skuDeclarationId;
    /**
     * 产地
     */
    private String orogin;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_SKU_ORIGIN_DECLARATION", sequenceName = "S_T_WH_SKU_ORIGIN_DECLARATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_SKU_ORIGIN_DECLARATION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_DECLARATION_ID")
    public Long getSkuDeclarationId() {
        return skuDeclarationId;
    }

    public void setSkuDeclarationId(Long skuDeclarationId) {
        this.skuDeclarationId = skuDeclarationId;
    }

    @Column(name = "ORIGIN")
    public String getOrogin() {
        return orogin;
    }

    public void setOrogin(String orogin) {
        this.orogin = orogin;
    }



}
