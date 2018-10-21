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
@Table(name = "t_bi_channel_sku_supplies")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannelSkuSupplies extends BaseModel {



    /**
     * 
     */
    private static final long serialVersionUID = 3458063127961805798L;

    private Long id;

    private Long shopId;

    private Long skuId;

    private Long paperSku;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_BI_CHANNEL_SKU", sequenceName = "SEQ_T_BI_CHANNEL_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BI_CHANNEL_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "PAPER_SKU")
    public Long getPaperSku() {
        return paperSku;
    }

    public void setPaperSku(Long paperSku) {
        this.paperSku = paperSku;
    }



}
