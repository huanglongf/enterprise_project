package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;

@Entity
@Table(name = "t_wh_sta_cancel")
public class StaCancel extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2217598546572719382L;

    /**
     * PK
     */
    private Long id;

    /**
     * 店铺
     */
    private String owner;

    /**
     * 商品数量
     */
    private Long quantity;

    private MsgOutboundOrderCancel orderCancel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 行号
     */
    private String orderLineNo;

    /**
     * 订单来源
     */
    private String orderSourcePlatform;

    /**
     * 是否短配
     */
    private Boolean isShortPicking = false;

    /**
     * 销售(销售单行单价)
     */
    private BigDecimal unitPrice;

    /**
     * 采购(采购行总金额)/销售(销售单行实际总金额)
     */
    private BigDecimal totalActual;

    private Sku sku;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_CANCEL", sequenceName = "S_t_wh_sta_cancel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_CANCEL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_cancel_id")
    @Index(name = "IDX_order_cancel_id")
    public MsgOutboundOrderCancel getOrderCancel() {
        return orderCancel;
    }

    public void setOrderCancel(MsgOutboundOrderCancel orderCancel) {
        this.orderCancel = orderCancel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @Index(name = "IDX_sku_id")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "order_line_no")
    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    @Column(name = "order_source_platform")
    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "is_short_picking")
    public Boolean getIsShortPicking() {
        return isShortPicking;
    }

    public void setIsShortPicking(Boolean isShortPicking) {
        this.isShortPicking = isShortPicking;
    }

    @Column(name = "unit_price")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "total_actual")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

}
