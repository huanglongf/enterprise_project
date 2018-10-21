package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author bin.hu sta指定区域库存占用数据
 */
@Entity
@Table(name = "T_WH_STA_OCP_LINE")
public class StaOcpLine extends BaseModel {


    private static final long serialVersionUID = -8545327220493991275L;

    private Long id;
    /**
     * staid
     */
    private Long staId;

    /**
     * stalineid
     */
    private Long staLineId;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 商品ID
     */
    private Long skuId;
    /**
     * 占用数量
     */
    private Integer quantity;
    /**
     * 占用区域
     */
    private String ocpCode;
    /**
     * 仓库ID
     */
    private Long ouId;


    @Id
    @SequenceGenerator(name = "S_T_WH_STA_OCP_LINE", sequenceName = "S_T_WH_STA_OCP_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_STA_OCP_LINE")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
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

    @Column(name = "STA_LINE_ID")
    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "QUANTITY")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "OCP_CODE")
    public String getOcpCode() {
        return ocpCode;
    }

    public void setOcpCode(String ocpCode) {
        this.ocpCode = ocpCode;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


}
