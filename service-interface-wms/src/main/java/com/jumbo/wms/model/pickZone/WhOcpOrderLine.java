package com.jumbo.wms.model.pickZone;

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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;


/**
 * The persistent class for the T_WH_OCP_ORDER_LINE database table.
 * 
 */
@Entity
@Table(name = "T_WH_OCP_ORDER_LINE")
public class WhOcpOrderLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7651132999608164381L;
    private Long id;
    /**
     * 确认数量
     */
    private Integer conformQty;
    /**
     * 占用批
     */
    private WhOcpOrder whOcpOrder;
    /**
     * 渠道
     */
    private String owner;
    /**
     * 计划量
     */
    private Integer qty;
    /**
     * 商品
     */
    private Sku sku;

    /**
     * 占用区域
     */
    private String ocpCode;

    @Id
    @SequenceGenerator(name = "T_WH_OCP_ORDER_LINE_ID_GENERATOR", sequenceName = "S_T_WH_OCP_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_OCP_ORDER_LINE_ID_GENERATOR")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "CONFORM_QTY")
    public Integer getConformQty() {
        return this.conformQty;
    }

    public void setConformQty(Integer conformQty) {
        this.conformQty = conformQty;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCP_ID")
    public WhOcpOrder getWhOcpOrder() {
        return whOcpOrder;
    }



    public void setWhOcpOrder(WhOcpOrder whOcpOrder) {
        this.whOcpOrder = whOcpOrder;
    }


    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public Integer getQty() {
        return this.qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }


    public void setSku(Sku sku) {
        this.sku = sku;
    }


    @Override
    public String toString() {
        return "WhOcpOrderLine [id=" + id + ", conformQty=" + conformQty + ", whOcpOrder=" + whOcpOrder.getId() + ", owner=" + owner + ", qty=" + qty + ", sku=" + sku.getId() + "]";
    }

    @Column(name = "ocp_code")
    public String getOcpCode() {
        return ocpCode;
    }

    public void setOcpCode(String ocpCode) {
        this.ocpCode = ocpCode;
    }


}
