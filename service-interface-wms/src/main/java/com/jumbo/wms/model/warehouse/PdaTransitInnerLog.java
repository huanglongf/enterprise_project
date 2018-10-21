package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_pda_transitinner_log")
public class PdaTransitInnerLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3303902643731811287L;

    private Long id;

    private Long userId;

    private Long skuId;

    private Long ouId;

    private Long disId;

    private Long locId;

    private Long qty;

    private Long targetDisId;

    private Long targetLocId;

    private String owner;

    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "s_t_pda_transitinner_log", sequenceName = "s_t_pda_transitinner_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_t_pda_transitinner_log")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "sku_id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "dis_id")
    public Long getDisId() {
        return disId;
    }

    public void setDisId(Long disId) {
        this.disId = disId;
    }

    @Column(name = "loc_id")
    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "target_dis_id")
    public Long getTargetDisId() {
        return targetDisId;
    }

    public void setTargetDisId(Long targetDisId) {
        this.targetDisId = targetDisId;
    }

    @Column(name = "target_loc_id")
    public Long getTargetLocId() {
        return targetLocId;
    }

    public void setTargetLocId(Long targetLocId) {
        this.targetLocId = targetLocId;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
