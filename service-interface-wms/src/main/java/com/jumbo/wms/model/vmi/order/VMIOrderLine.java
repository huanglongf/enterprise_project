package com.jumbo.wms.model.vmi.order;

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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_VMI_ORDER_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VMIOrderLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5542252184755684660L;

    private Long id;
    // 宝尊订单号
    private String code;
    // 订单号序号
    private String lineSequence;
    // 产品编码
    private String productCode;
    // 条形码
    private String barCode;
    // 颜色
    private String color;
    // 尺码
    private String size;
    // 单价
    private BigDecimal unitPrice;
    // 数量
    private Integer quantity;
    // 金额
    private BigDecimal total;
    // 折扣
    private BigDecimal discount;

    private Date createTime;

    private VMIOrder vmiOrder;

    private String productDesc;

    private BigDecimal originalPrice;

    private String lineReserve1;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_ORDER_LINE", sequenceName = "S_T_VMI_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_ORDER_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "line_Sequence", length = 5)
    public String getLineSequence() {
        return lineSequence;
    }

    public void setLineSequence(String lineSequence) {
        this.lineSequence = lineSequence;
    }

    @Column(name = "PRODUCT_CODE", length = 20)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "bar_Code", length = 20)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "color", length = 20)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "SKU_SIZE", length = 20)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "unitPrice")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VMIORDER_ID")
    @Index(name = "IDX_VMIORDER_LINE")
    public VMIOrder getVmiOrder() {
        return vmiOrder;
    }

    public void setVmiOrder(VMIOrder vmiOrder) {
        this.vmiOrder = vmiOrder;
    }

    @Column(name = "PRODUCT_DESC", length = 100)
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Column(name = "ORIGINAL_PRICE")
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Column(name = "LINE_RESERVE1", length = 500)
    public String getLineReserve1() {
        return lineReserve1;
    }

    public void setLineReserve1(String lineReserve1) {
        this.lineReserve1 = lineReserve1;
    }


}
