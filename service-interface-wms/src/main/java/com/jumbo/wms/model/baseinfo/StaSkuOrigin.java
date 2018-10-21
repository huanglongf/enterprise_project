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
@Table(name = "T_WH_STA_SKU_ORIGIN")
public class StaSkuOrigin extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3732899191669386639L;

    /**
     * PK
     */
    private Long id;
    /**
     * 商品关联id
     */
    private Long staId;

    private Long qty;

    private String skuCode;

    /**
     * 产地
     */
    private String origin;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "s_T_WH_STA_SKU_ORIGIN", sequenceName = "s_T_WH_STA_SKU_ORIGIN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_T_WH_STA_SKU_ORIGIN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "ORIGIN")
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }



}
