package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

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

/**
 * 菜鸟入库单确认--商品明细行
 * 
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_CONFIRM_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnStockInOrderLine implements Serializable {

    private static final long serialVersionUID = -3375972158776833580L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜鸟入库确认信息
     */
    private CnWmsStockInOrderConfirm stockInOrderConfirm;
    /**
     * 入库单明细ID
     */
    private String orderItemId;
    /**
     * sku重量 单位克,采购入库单、普通入库单必传
     */
    private Long weight;
    /**
     * 体积 单位立方厘米 采购入库单、普通入库单必传
     */
    private Integer volume;
    /**
     * 长 单位 mm 采购入库单、普通入库单必传
     */
    private Integer length;
    /**
     * 宽 单位 mm 采购入库单、普通入库单必传
     */
    private Integer width;
    /**
     * 高 单位 mm 采购入库单、普通入库单必传
     */
    private Integer height;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_STOCK_IN_CONFIRM_L", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_IN_CONFIRM_ID")
    public CnWmsStockInOrderConfirm getStockInOrderConfirm() {
        return stockInOrderConfirm;
    }

    public void setStockInOrderConfirm(CnWmsStockInOrderConfirm stockInOrderConfirm) {
        this.stockInOrderConfirm = stockInOrderConfirm;
    }

    @Column(name = "ORDER_ITEM_ID")
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    @Column(name = "WEIGHT")
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Column(name = "LENGTH")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Column(name = "WIDTH")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "HEIGHT")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
