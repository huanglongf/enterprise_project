package com.jumbo.wms.model.pda;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * @author hui.li
 *
 */
@Entity
@Table(name = "t_wh_picking_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PickingLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7741243259721344484L;

    /**
     * PK
     */
    private Long id;
   

    /**
     * 商品
     */
    private Sku sku;

    /**
     * 库位
     */
    private WarehouseLocation location;

    /**
     * 拣货批次
     */
    private WhPickingBatch whPickingBatch;

    /**
     * 拣货区域
     */
    private WhPickZoon whPickZoon;

    /**
     * 计划执行量
     */
    private Long planQty;

    /**
     * 生产日期
     */
    private Date proDate;

    /**
     * 过期日期
     */
    private Date expDate;

    /**
     * 拣货数量
     */
    private Long qty;

    /**
     * 库存状态
     */
    private InventoryStatus inventoryStatus;
    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_PICKING_OP_LINE", sequenceName = "S_T_WH_PICKING_OP_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_PICKING_OP_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "PRO_DATE")
    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_id")
    public WhPickingBatch getWhPickingBatch() {
        return whPickingBatch;
    }

    public void setWhPickingBatch(WhPickingBatch whPickingBatch) {
        this.whPickingBatch = whPickingBatch;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickzoon_id")
    public WhPickZoon getWhPickZoon() {
        return whPickZoon;
    }

    public void setWhPickZoon(WhPickZoon whPickZoon) {
        this.whPickZoon = whPickZoon;
    }

    @Column(name = "p_qty")
    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inv_status_id")
    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }



}
