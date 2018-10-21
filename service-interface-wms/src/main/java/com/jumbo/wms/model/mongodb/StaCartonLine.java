package com.jumbo.wms.model.mongodb;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 
 * 箱明细
 */
@Entity
@Table(name = "t_wh_sta_carton_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCartonLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5331683187047381136L;

    private Long id;

    private StaCarton staCartonId; // 箱ID

    private Long skuId;

    private Date expDate; // 过期时间

    private Long qty;

    private Integer status;// 1:明细上架完成


    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_CONTAINER_LINE", sequenceName = "S_T_WH_CONTAINER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_CONTAINER_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    @Index(name = "IDX_STA_CARTON_LINE_ID")
    public StaCarton getStaCartonId() {
        return staCartonId;
    }

    public void setStaCartonId(StaCarton staCartonId) {
        this.staCartonId = staCartonId;
    }

    @Column(name = "sku_id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "exp_date")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

}
