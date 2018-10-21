package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库拣货去补货配置
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_PICKING_REPLENISH_CFG", uniqueConstraints = {@UniqueConstraint(columnNames = {"SKU_ID", "OU_ID", "SHOP_OWNER"})})
public class PickingReplenishCfg extends BaseModel {

    private static final long serialVersionUID = -442703969949657906L;
    /**
     * PK
     */
    private Long id;
    /**
     * 商品
     */
    private Sku sku;
    /**
     * 库区
     */
    private WarehouseDistrict district;
    /**
     * 库区最大存货量
     */
    private Long maxQty;
    /**
     * 补货警示百分比
     */
    private BigDecimal warningPre;
    /**
     * 仓库组织
     */
    private OperationUnit ou;
    /**
     * 所属店铺 非共享仓使用 KJL
     */
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PRC", sequenceName = "S_T_WH_PICKING_REPLENISH_CFG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_PRC_SKUID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    @Index(name = "IDX_PRC_DISTRICTID")
    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    @Column(name = "MAX_QTY")
    public Long getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(Long maxQty) {
        this.maxQty = maxQty;
    }

    @Column(name = "WARNING_PRE")
    public BigDecimal getWarningPre() {
        return warningPre;
    }

    public void setWarningPre(BigDecimal warningPre) {
        this.warningPre = warningPre;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_PRC_OUID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "SHOP_OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
