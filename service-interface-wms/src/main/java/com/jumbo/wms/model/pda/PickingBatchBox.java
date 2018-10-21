package com.jumbo.wms.model.pda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * @author hui.li
 *
 */
@Entity
@Table(name = "t_wh_picking_batch_box")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PickingBatchBox extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -4998808577699070722L;


    /**
     * PK
     */
    private Long id;

    /**
     * 周转箱编码
     */
    private String boxCode;

    /**
     * 小批次ID
     */
    private Long pickingBatchId;

    /**
     * 货物数量
     */
    private Long qty;

    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_PICKING_BATCH_BOX", sequenceName = "S_T_WH_PICKING_BATCH_BOX", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_PICKING_BATCH_BOX")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "box_code")
    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    @Column(name = "picking_batch_id")
    public Long getPickingBatchId() {
        return pickingBatchId;
    }

    public void setPickingBatchId(Long pickingBatchId) {
        this.pickingBatchId = pickingBatchId;
    }


}
