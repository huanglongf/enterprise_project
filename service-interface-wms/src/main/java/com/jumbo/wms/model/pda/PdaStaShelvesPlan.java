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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * 
 * @author lizaibiao
 * 
 */
@Entity
@Table(name = "t_wh_sta_shelves_plan")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaStaShelvesPlan extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6548919008012748110L;

    private Long id;// 主键

    private Sku sku;// 商品

    private WarehouseLocation location;// 库位


    private StockTransApplication sta;// 作业单

    private StaCarton staCarton;// 收货箱


    private Long qty;// 数量


    private Date expDate;// 过期时间


    /**
     * 是否上架完成
     */
    // private Boolean status;

    /**
     * 是否上架完成
     */
    private Long status;// 0:未上架 1：已加入上架还未完成 2：已经上架完成

    /**
     * 箱明细id
     * 
     * @return
     */
    private Long staCartonLineId;

    @Column(name = "STA_CARTON_LINE_ID")
    public Long getStaCartonLineId() {
        return staCartonLineId;
    }


    public void setStaCartonLineId(Long staCartonLineId) {
        this.staCartonLineId = staCartonLineId;
    }


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "s_t_wh_sta_shelves_plan", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_FK_SKU_ID")
    public Sku getSku() {
        return sku;
    }


    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_FK_LOCATION_ID")
    public WarehouseLocation getLocation() {
        return location;
    }


    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_FK_STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }


    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }


    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }


    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_CARTON_ID")
    @Index(name = "IDX_FK_STA_CARTON_ID")
    public StaCarton getStaCarton() {
        return staCarton;
    }

    public void setStaCarton(StaCarton staCarton) {
        this.staCarton = staCarton;
    }

    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }


    public void setStatus(Long status) {
        this.status = status;
    }

    /*
     * @Column(name = "STATUS") public Boolean getStatus() { return status; }
     * 
     * 
     * public void setStatus(Boolean status) { this.status = status; }
     */


}
