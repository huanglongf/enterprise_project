package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name="t_wh_in_agv_to_hub_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InboundAgvToHubLine  extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 8641917982354878666L;
    
    private Long id;
    
    private Long qty;
    
    private Long skuId;
    
    private Long inAgvId;
    
    private String expireDate; 

    @Id
    @Column(name="ID")
    @SequenceGenerator(name = "seq_t_wh_in_agv_to_hub_line", sequenceName = "seq_t_wh_in_agv_to_hub_line", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_t_wh_in_agv_to_hub_line")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name="IN_AGV_ID")
    public Long getInAgvId() {
        return inAgvId;
    }

    public void setInAgvId(Long inAgvId) {
        this.inAgvId = inAgvId;
    }

    @Column(name="EXPIRE_DATE")
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    
}
