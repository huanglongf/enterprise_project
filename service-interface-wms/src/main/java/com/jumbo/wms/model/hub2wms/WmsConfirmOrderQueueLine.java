package com.jumbo.wms.model.hub2wms;

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
@Table(name = "T_WH_CONFIRM_ORDER_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WmsConfirmOrderQueueLine extends BaseModel {

    private static final long serialVersionUID = 6421438019761102207L;

    private Long id;// 主键
    private String lineNo;// 行号
    private Integer orderQty;// 订单计划量
    private Integer qty;// 实际数量
    private String plLineNo;// 平台行号
    private Long skuId;// 商品
    private Long cId;// 外键

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_CONFIRM_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LINE_NO")
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "ORDER_QTY")
    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "PL_LINE_NO")
    public String getPlLineNo() {
        return plLineNo;
    }

    public void setPlLineNo(String plLineNo) {
        this.plLineNo = plLineNo;
    }



    @Column(name = "C_ID")
    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

}
