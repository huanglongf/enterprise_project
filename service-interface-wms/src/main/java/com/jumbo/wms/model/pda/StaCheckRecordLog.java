package com.jumbo.wms.model.pda;

import java.util.Date;

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
 * 二次分拣核对记录
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_STA_CHECK_RECORD_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCheckRecordLog extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1450145323958513474L;
    /**
     * PK
     */
    private Long id;

    private Long pickingId;


    private Long staId;


    private Long staLineId;


    private Long operatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private String skuBarCode;
    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TWSCRL", sequenceName = "S_T_WH_STA_CHECK_RECORD_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TWSCRL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "PICKING_ID")
    public Long getPickingId() {
        return pickingId;
    }

    public void setPickingId(Long pickingId) {
        this.pickingId = pickingId;
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

    @Column(name = "OPERATOR_ID")
    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    @Column(name = "SKU_BAR_CODE")
    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }


}
